package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.dto.RegistrationRequest;
import com.follabj_be.follabj_be.service.RegistrationInterface;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RegistrationController {
    private final RegistrationInterface registrationInterface;

    public RegistrationController(RegistrationInterface registrationInterface) {
        this.registrationInterface = registrationInterface;
    }


    @PostMapping(path = "signup")
    public Map<String, String> register(@RequestBody RegistrationRequest request) {
        Map<String, String> res = new HashMap<>();
        res.put("status", "200");
        res.put("message", registrationInterface.register(request));
        return res;
        //return "registered";
    }

    @GetMapping(path = "confirm")
    public Map<String, String> confirm(@RequestParam("token") String token) {
        Map<String, String> res = new HashMap<>();
        res.put("status", "200");
        res.put("message", registrationInterface.confirmToken(token));
        return res;
    }
}
