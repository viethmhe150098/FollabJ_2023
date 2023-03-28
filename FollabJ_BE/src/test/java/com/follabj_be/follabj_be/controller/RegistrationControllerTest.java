package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.dto.RegistrationRequest;
import com.follabj_be.follabj_be.service.RegistrationInterface;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrationControllerTest {

    @Mock
    private RegistrationInterface registrationInterface;

    @InjectMocks
    private RegistrationController registrationController;

    @Test
    @DisplayName("should return an error message when the request is invalid")
    void registerWithInvalidRequest() {
        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("");
        request.setEmail("");
        request.setPassword("");
        String result = registrationController.register(request);
        assertEquals(null, result);
    }

    @Test
    @DisplayName("should register a new user with valid request")
    void registerWithValidRequest() {
        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("test");
        request.setEmail("test@test.com");
        request.setPassword("test");

        when(registrationInterface.register(request)).thenReturn("User registered successfully");

        String response = registrationController.register(request);

        assertEquals("User registered successfully", response);
    }
}