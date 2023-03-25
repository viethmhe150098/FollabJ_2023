package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.dto.*;
import com.follabj_be.follabj_be.entity.Invitation;
import com.follabj_be.follabj_be.entity.LeaderRequest;
import com.follabj_be.follabj_be.service.impl.LeaderRequestService;
import com.follabj_be.follabj_be.service.impl.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final LeaderRequestService leaderRequestService;

    @GetMapping("/find")
    @PreAuthorize("hasAnyAuthority('LEADER', 'ADMIN')")
    public List<UserDTO> findUserInfoByEmail(@RequestParam String email_cha){
        return userService.findUsersByEmail(email_cha);
    }

//    @GetMapping("{user_id}/invitation")
//    @PreAuthorize("hasAuthority('ACTIVE_USER')")
//    public ResponseEntity<List<Invitation>> getInvitations(@PathVariable Long user_id) {
//        List<Invitation> list = userService.getAllInvitation(user_id);
//
//        return new ResponseEntity<>(list, HttpStatus.OK);
//    }

    @PostMapping("/request")
    @PreAuthorize("hasAuthority('ACTIVE_USER')")
    public ResponseEntity<String> sendRequest(@RequestBody LeaderRequestDTO leaderRequestDTO){
        String message = leaderRequestService.saveRequest(leaderRequestDTO);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/{u_id}")
    @PreAuthorize("hasAuthority('ACTIVE_USER')")
    public ResponseEntity<Map<Object, Object>> getUserProfile(@PathVariable Long u_id){
        AppUserDTO aud = userService.getUserProfile(u_id);
        Map<Object, Object> res = new HashMap<>();
        res.put("status", HttpStatus.OK);
        res.put("message", "Found user "+u_id);
        res.put("userInfo", aud);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/password/{u_id}")
    @PreAuthorize("hasAuthority('ACTIVE_USER')")
    public ResponseEntity<Map<String, String>> changePassword (@PathVariable Long u_id, @RequestBody PasswordDTO passwordDTO){
        Map<String, String> res = new HashMap<>();
        res.put("message", userService.changePassword(passwordDTO, u_id));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/info/{id}")
    @PreAuthorize("hasAuthority('ACTIVE_USER')")
    public ResponseEntity<Map<String, String>> updateProfile (@PathVariable Long id, @RequestBody UpdateUserDTO updateUserDTO){
        Map<String, String> res = userService.updateUser(updateUserDTO, id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
