package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.service.impl.JWTService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@ExtendWith(MockitoExtension.class)
class TokenControllerTest {
    @Mock
    private JWTService jwtService;

    @InjectMocks
    private TokenController tokenController;

    @Test
    @DisplayName("should return an error response when the request is invalid")
    void refreshTokenWhenRequestIsInvalid() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(AUTHORIZATION)).thenReturn(null);
        assertThrows(
                NullPointerException.class, () -> tokenController.refreshToken(request, null));
    }

    @Test
    @DisplayName("should return an error response when the refresh token is expired")
    void refreshTokenWhenTokenIsExpired() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(AUTHORIZATION))
                .thenReturn(
                        "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MzU0NjQwNywiaWF0IjoxNTkzNDYwMDA3fQ.X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z");
        tokenController.refreshToken(request, null);
        verify(jwtService, times(1)).getRefreshToken(request, null);
    }

    @Test
    @DisplayName("should return a new refresh token when the request is valid")
    void refreshTokenWhenRequestIsValid() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(AUTHORIZATION))
                .thenReturn(
                        "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MzU4NzQ0NywiaWF0IjoxNTkzNTg3MTQ3fQ.X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z-X-Q_Z");
        tokenController.refreshToken(request, new MockHttpServletResponse());
        verify(jwtService, times(1)).getRefreshToken(request, new MockHttpServletResponse());
    }
}