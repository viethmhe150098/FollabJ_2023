package com.follabj_be.follabj_be.service.impl;

import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.ConfirmToken;
import com.follabj_be.follabj_be.repository.TokenRepository;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.quality.Strictness;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {
    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private TokenService tokenService;

    private AppUser appUser;
    @Rule
    public MockitoRule rule = MockitoJUnit.rule().strictness(Strictness.LENIENT);

    @BeforeEach
    void setUp() {
        appUser = new AppUser(1L, "aaa", "test@test.com");
    }

    @Test
    @DisplayName("Should return 0 when the token is not found")
    void setConfirmedAtWhenTokenNotFound() {
        String token = "nonExistentToken";
        int result = tokenService.setConfirmedAt(token);
        assertEquals(0, result);
        verify(tokenRepository, times(0)).updateConfirmedAt(token, LocalDateTime.now());
    }

    @Test
    @DisplayName("Should set confirmedAt when the token is valid")
    void setConfirmedAtWhenTokenIsValid() {
        String token = "validToken";
        ConfirmToken confirmToken =
                new ConfirmToken(
                        token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), appUser);
        int result = tokenService.setConfirmedAt(token);
        assertEquals(0, result);
        verify(tokenRepository, times(0)).findByToken(token);
        verify(tokenRepository, times(0)).updateConfirmedAt(token, LocalDateTime.now());
    }

    @Test
    @DisplayName("Should return an empty optional when the token does not exist in the repository")
    void getTokenWhenTokenDoesNotExist() {
        String token = "nonexistent-token";
        when(tokenRepository.findByToken(token)).thenReturn(Optional.empty());

        Optional<ConfirmToken> result = tokenService.getToken(token);

        assertTrue(result.isEmpty());
        verify(tokenRepository, times(1)).findByToken(token);
    }

    @Test
    @DisplayName("Should return the token when it exists in the repository")
    void getTokenWhenTokenExists() {
        String tokenValue = "test-token";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiresAt = createdAt.plusHours(24);
        ConfirmToken confirmToken = new ConfirmToken(tokenValue, createdAt, expiresAt, appUser);

        when(tokenRepository.findByToken(tokenValue)).thenReturn(Optional.of(confirmToken));

        Optional<ConfirmToken> result = tokenService.getToken(tokenValue);

        assertTrue(result.isPresent());
        assertEquals(confirmToken, result.get());
        verify(tokenRepository, times(1)).findByToken(tokenValue);
    }

    @Test
    @DisplayName("Should save the confirmation token successfully")
    void saveConfirmationToken() {
        ConfirmToken confirmToken =
                new ConfirmToken(
                        "test-token",
                        LocalDateTime.now(),
                        LocalDateTime.now().plusHours(1),
                        appUser);
        when(tokenRepository.save(confirmToken)).thenReturn(confirmToken);

        tokenService.saveConfirmationToken(confirmToken);

        verify(tokenRepository, times(1)).save(confirmToken);
    }
}