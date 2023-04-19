package com.follabj_be.follabj_be.service.impl;

import com.follabj_be.follabj_be.entity.AppUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@ExtendWith(MockitoExtension.class)
class JWTServiceTest {
    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private JWTService jwtService;

    @Test
    @DisplayName("Should throw an exception when the refresh token is missing")
    void getRefreshTokenWhenRefreshTokenIsMissingThenThrowException() {
        when(request.getHeader(AUTHORIZATION)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> jwtService.getRefreshToken(request, response));
        verify(response, never()).setHeader(anyString(), anyString());
        verify(response, never()).setStatus(anyInt());
    }

//    @Test
//    @DisplayName("Should return new access token and refresh token when the refresh token is valid")
//    void getRefreshTokenWhenRefreshTokenIsValid() throws IOException {
//        String validRefreshToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGFuZ3R2aGUxNTEzMDdAZnB0LmVkdS52biIsImV4cCI6MTY4MTg5OTY4M30.iBoeuOzV6k3Bkt4Fb9BP7P9e21hpRZGdoFzEKS4ruoY";
//        String userEmail = "thangtvhe151307@fpt.edu.vn";
//        AppUser appUser = new AppUser(5L, "thangtvhe151308", userEmail);
//        appUser.setRoles(new HashSet<>());
//
//        when(request.getHeader(AUTHORIZATION)).thenReturn(validRefreshToken);
//        when(userService.getUserByEmail(userEmail)).thenReturn(appUser);
//
//        Map<String, String> tokens = jwtService.getRefreshToken(request, response);
//
//        assertNotNull(tokens);
//        assertTrue(tokens.containsKey("access_token"));
//        assertTrue(tokens.containsKey("refresh_token"));
//        assertEquals(validRefreshToken.substring("Bearer ".length()), tokens.get("refresh_token"));
//
//        verify(request, times(1)).getHeader(AUTHORIZATION);
//        verify(userService, times(1)).getUserByEmail(userEmail);
//    }
}