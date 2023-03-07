package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.dto.LeaderRequestDTO;
import com.follabj_be.follabj_be.dto.UserDTO;
import com.follabj_be.follabj_be.entity.Invitation;
import com.follabj_be.follabj_be.entity.LeaderRequest;
import com.follabj_be.follabj_be.service.impl.LeaderRequestService;
import com.follabj_be.follabj_be.service.impl.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final LeaderRequestService leaderRequestService;

    @GetMapping("/find")
    @PreAuthorize("hasAuthority('LEADER')")
    public List<UserDTO> findUserInfoByEmail(@RequestParam String email_cha){
        return userService.findUsersByEmail(email_cha);
    }

    @GetMapping("{user_id}/invitation")
    @PreAuthorize("hasAuthority('ACTIVE_USER')")
    public ResponseEntity<List<Invitation>> getInvitations(@PathVariable Long user_id){
        List<Invitation> list = userService.getAllInvitation(user_id);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/request")
    @PreAuthorize("hasAuthority('ACTIVE_USER')")
    public ResponseEntity<String> sendRequest(@RequestBody LeaderRequestDTO leaderRequestDTO){
        String message = leaderRequestService.saveRequest(leaderRequestDTO);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
