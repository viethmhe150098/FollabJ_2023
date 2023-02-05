package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.requestModel.RegistrationRequest;
import com.follabj_be.follabj_be.service.dependency.RegistrationInterface;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {
    private final RegistrationInterface registrationInterface;

    public RegistrationController(RegistrationInterface registrationInterface) {
        this.registrationInterface = registrationInterface;
    }


    @PostMapping(path = "signup")
    public String register(@RequestBody RegistrationRequest request) {
        return registrationInterface.register(request);
        //return "registered";
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationInterface.confirmToken(token);
    }
}
