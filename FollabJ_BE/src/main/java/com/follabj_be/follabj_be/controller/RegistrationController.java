package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.requestModel.RegistrationRequest;
import com.follabj_be.follabj_be.service.RegistrationService;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {
    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping(path = "signup")
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
        //return "registered";
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
