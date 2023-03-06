package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.dto.UserDTO;
import com.follabj_be.follabj_be.entity.Invitation;
import com.follabj_be.follabj_be.service.impl.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/find")
    @PreAuthorize("hasAuthority('LEADER')")
    public List<UserDTO> findUserInfoByEmail(@RequestParam String email_cha) {
        return userService.findUsersByEmail(email_cha);
    }

    @GetMapping("{user_id}/invitation")
    @PreAuthorize("hasAuthority('ACTIVE_USER')")
    public ResponseEntity<List<Invitation>> getInvitations(@PathVariable Long user_id) {
        List<Invitation> list = userService.getAllInvitation(user_id);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
