package com.follabj_be.follabj_be.controller;

import com.amazonaws.services.quicksight.model.User;
import com.follabj_be.follabj_be.dto.*;
import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.Invitation;
import com.follabj_be.follabj_be.entity.LeaderRequest;
import com.follabj_be.follabj_be.service.impl.LeaderRequestService;
import com.follabj_be.follabj_be.service.impl.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final LeaderRequestService leaderRequestService;

    ModelMapper modelMapper;

    @GetMapping("/find")
    @PreAuthorize("hasAnyAuthority('LEADER', 'ADMIN')")
    public List<UserDTO> findUserInfoByEmail(@RequestParam String email_cha) {
        return userService.findUsersByEmail(email_cha);
    }

//    @GetMapping("{user_id}/invitation")
//    @PreAuthorize("hasAuthority('ACTIVE_USER')")
//    public ResponseEntity<List<Invitation>> getInvitations(@PathVariable Long user_id) {
//        List<Invitation> list = userService.getAllInvitation(user_id);
//
//        return new ResponseEntity<>(list, HttpStatus.OK);
//    }

    @GetMapping("/request")
    @PreAuthorize("hasAuthority('ACTIVE_USER')")
    public ResponseEntity<Map<Object, Object>> getUserRequest(Authentication authentication){
        Map<Object, Object> res = new HashMap<>();
        Long user_id = getUserId(authentication);

        LeaderRequest request = leaderRequestService.getRequestByUserId(user_id);
        if (request != null) {
            LeaderRequestDTO requestDTO = modelMapper.map(request, LeaderRequestDTO.class);
            res.put("content", requestDTO);
        } else {
            res.put("content", null);
        }
        res.put("status", HttpStatus.OK);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/request")
    @PreAuthorize("hasAuthority('ACTIVE_USER')")
    public ResponseEntity<String> sendRequest(Authentication authentication, @Valid @RequestBody LeaderRequestDTO leaderRequestDTO) {
        Long user_id = getUserId(authentication);
        UserDTO user = new UserDTO();
        user.setId(user_id);
        leaderRequestDTO.setUser(user);
        String message = leaderRequestService.saveRequest(leaderRequestDTO);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/{u_id}")
    @PreAuthorize("hasAuthority('ACTIVE_USER')")
    public ResponseEntity<Map<Object, Object>> getUserProfile(@PathVariable Long u_id) {
        AppUserDTO aud = userService.getUserProfile(u_id);
        Map<Object, Object> res = new HashMap<>();
        res.put("status", HttpStatus.OK);
        res.put("message", "Found user " + u_id);
        res.put("userInfo", aud);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/password/{u_id}")
    @PreAuthorize("hasAuthority('ACTIVE_USER')")
    public ResponseEntity<Map<String, String>> changePassword(@PathVariable Long u_id,@Valid @RequestBody PasswordDTO passwordDTO) {
        Map<String, String> res = new HashMap<>();
        res.put("message", userService.changePassword(passwordDTO, u_id));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/info/{id}")
    @PreAuthorize("hasAuthority('ACTIVE_USER')")
    public ResponseEntity<Map<String, String>> updateProfile(@PathVariable Long id,@Valid @RequestBody UpdateUserDTO updateUserDTO) {
        Map<String, String> res = userService.updateUser(updateUserDTO, id);
        return new ResponseEntity<>(res, HttpStatus.valueOf(Integer.parseInt(res.get("status"))));
    }

    @PostMapping("/forgot")
    public ResponseEntity<Map<String, String>> forgotPassword(@Valid @RequestBody ForgotUserDTO forgotUserDTO) {
        String message = userService.forgetPassword(forgotUserDTO.getEmail());
        Map<String, String> res = new HashMap<>();
        res.put("status", "200");
        res.put("message", message);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    public Long getUserId(Authentication authentication) {
        return userService.getUserByEmail(authentication.getPrincipal().toString()).getId();
    }
}
