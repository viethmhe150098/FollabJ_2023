package com.follabj_be.follabj_be.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.follabj_be.follabj_be.service.impl.JWTService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class TokenController {
    private final JWTService jwtService;

    public TokenController(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String> message = jwtService.getRefreshToken(request,response);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), message);
    }
}
