package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.dto.AppUserDTO;
import com.follabj_be.follabj_be.dto.LeaderRequestDTO;
import com.follabj_be.follabj_be.dto.UserDTO;
import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.LeaderRequest;
import com.follabj_be.follabj_be.service.impl.LeaderRequestService;
import com.follabj_be.follabj_be.service.impl.ProjectService;
import com.follabj_be.follabj_be.service.impl.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class AdminController {
    private final UserService userService;

    private final LeaderRequestService leaderRequestService;

    private final ModelMapper modelMapper;

    private final ProjectService projectService;

    @GetMapping("/admin/users")
    public List<UserDTO> getUsers(){
        List<AppUser> userList = userService.getAllUsers();
        List<UserDTO> dtoList = new ArrayList<>();

        for( AppUser user: userList) {
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);

            dtoList.add(userDTO);
        }

        return dtoList;
    }

    @PostMapping("/admin/users")
    @PreAuthorize("hasAuthority('AMDIN')")
    public ResponseEntity<Map<Object, Object>> updateUserStatus(@PathVariable Long u_id, @RequestParam int status){
        AppUserDTO aud = userService.updateStatus(status, u_id);
        Map<Object, Object> res = new HashMap<>();
        if(aud != null) {
            res.put("status", HttpStatus.OK);
            res.put("message", "Update status for user "+ u_id);
            res.put("user", aud);

        }else{
            res.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            res.put("message", "Something went wrong");
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

//    @GetMapping("/admin/request")
//    public List<LeaderRequestDTO> getLeaderRequest() {
//        List<LeaderRequest> requestList = leaderRequestService.getListRequest(0).toList();
//        List<LeaderRequestDTO> dtoList = new ArrayList<>();
//
//        for (LeaderRequest request : requestList) {
//            LeaderRequestDTO requestDTO = new LeaderRequestDTO();
//            requestDTO.setU_id(request.getUser().getId());
//            requestDTO.setU_fullname(request.getUser_fullname());
//            request.setUser_id_number(request.getUser_id_number());
//
//            dtoList.add(requestDTO);
//        }
//
//        return dtoList;
//    }
//
//    @GetMapping("/admin/request/accept")
//    public void acceptLeaderRequest(@RequestParam Long request_id) {
//        leaderRequestService.updateRequestStatus(request_id, 1);
//    }
    @GetMapping("/admin/cu")
    public ResponseEntity<Map<String, String>> countUser(@RequestParam String by){
        String result = userService.count(by);
        Map<String, String> res = new HashMap<>();
        res.put("status", "200");
        res.put("message", result);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/admin/cp")
    public ResponseEntity<Map<String, String>> countProject(@RequestParam String by){
        String result = projectService.count(by);
        Map<String, String> res = new HashMap<>();
        res.put("status", "200");
        res.put("message", result);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
