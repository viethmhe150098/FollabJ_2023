package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.dto.AppUserDTO;
import com.follabj_be.follabj_be.service.impl.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class AdminController {
    private final UserService userService;

    @PostMapping("/admin/users")
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
}
