package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.dto.RegistrationRequest;
import com.follabj_be.follabj_be.service.RegistrationInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RegistrationController {
    private final RegistrationInterface registrationInterface;

    public RegistrationController(RegistrationInterface registrationInterface) {
        this.registrationInterface = registrationInterface;
    }


    @PostMapping(path = "signup")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody RegistrationRequest request) {
        Map<String, String> res = new HashMap<>();
        String token = registrationInterface.register(request);
        res.put("status", "200");
        res.put("token", token);
        return new ResponseEntity<>(res, HttpStatus.OK);

        //return "registered";
    }

    @GetMapping(path = "confirm")
    public ResponseEntity<Map<String, String>> confirm(@RequestParam("token") String token) {
        Map<String, String> res = new HashMap<>();
        String message = registrationInterface.confirmToken(token);
        res.put("status", "200");
        res.put("message", message);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
