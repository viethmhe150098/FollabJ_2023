package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.dto.AppUserDTO;
import com.follabj_be.follabj_be.dto.LeaderRequestDTO;
import com.follabj_be.follabj_be.dto.ProjectCountDTO;
import com.follabj_be.follabj_be.dto.UserDTO;
import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.LeaderRequest;
import com.follabj_be.follabj_be.entity.Project;
import com.follabj_be.follabj_be.service.impl.LeaderRequestService;
import com.follabj_be.follabj_be.service.impl.ProjectService;
import com.follabj_be.follabj_be.service.impl.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
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
            if (user.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"))) continue;
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);

            dtoList.add(userDTO);
        }

        return dtoList;
    }

    @PostMapping("/admin/users/{u_id}/update")
    @PreAuthorize("hasAuthority('ADMIN')")
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
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    @GetMapping("/admin/cu")
    public ResponseEntity<Map<String, String>> countUser(@RequestParam String by){
        String result = userService.count(by);
        Map<String, String> res = new HashMap<>();
        res.put("status", "200");
        res.put("message", result);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/admin/cp")
    public ResponseEntity<Map<Object, Object>> countProject(){
        String month = projectService.count("MONTH");
        String year = projectService.count("YEAR");
        String day = projectService.count("DAY");
        List<ProjectCountDTO> projectsPerMonth = projectService.projectsPerMonth();
        List<ProjectCountDTO> projectsPerDay = projectService.projectsPerDay();
        List<ProjectCountDTO> projectsPerYear = projectService.projectsPerYear();
        Map<Object, Object> res = new HashMap<>();
        res.put("status", "200");
        res.put("by_day", day);
        res.put("by_month", month);
        res.put("by_year", year);
        res.put("projectsPerDay", projectsPerDay);
        res.put("projectsPerMonth", projectsPerMonth);
        res.put("projectsPerYear", projectsPerYear);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/admin/getprj")
    public ResponseEntity<Map<Object, Object>> getAllProject(){
        Map<Object, Object> res = new HashMap<>();
        List<Project> result =  projectService.getAll();
//        res.put("status", "200");
//        res.put("curr_page", result.getNumber());
//        res.put("total_page", result.getTotalPages());
        res.put("data", result);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
