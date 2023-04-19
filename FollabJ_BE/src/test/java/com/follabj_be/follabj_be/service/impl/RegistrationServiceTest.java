package com.follabj_be.follabj_be.service.impl;

import com.follabj_be.follabj_be.dto.RegistrationRequest;
import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.ConfirmToken;
import com.follabj_be.follabj_be.service.EmailSender;
import com.follabj_be.follabj_be.service.TokenInterface;
import com.follabj_be.follabj_be.service.UserInterface;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @Mock
    private UserInterface userInterface;

    @Mock
    private TokenInterface tokenInterface;

    @Mock
    private EmailSender emailSender;

    @Mock
    private BuildEmail buildEmail;

    @InjectMocks
    private RegistrationService registrationService;

    @Test
    @DisplayName("Should throw an exception when the token is not found")
    void confirmTokenWhenTokenNotFoundThenThrowException() {
        String token = "invalidToken";
        when(tokenInterface.getToken(token)).thenReturn(Optional.empty());

        // Act and Assert
        IllegalStateException exception =
                assertThrows(
                        IllegalStateException.class, () -> registrationService.confirmToken(token));
        assertEquals("Token not found!", exception.getMessage());
        verify(tokenInterface, times(1)).getToken(token);
    }

    @Test
    @DisplayName("Should throw an exception when the email is already confirmed")
    void confirmTokenWhenEmailAlreadyConfirmedThenThrowException() {
        String token = "testToken";
        ConfirmToken confirmToken = new ConfirmToken();
        confirmToken.setConfirmedAt(LocalDateTime.now());
        when(tokenInterface.getToken(token)).thenReturn(Optional.of(confirmToken));

        IllegalStateException exception =
                assertThrows(
                        IllegalStateException.class, () -> registrationService.confirmToken(token));

        assertEquals("Email is already confirmed", exception.getMessage());
        verify(tokenInterface, times(1)).getToken(token);
    }

    @Test
    @DisplayName("Should throw an exception when the token is expired")
    void confirmTokenWhenTokenExpiredThenThrowException() {
        String token = "expiredToken";
        LocalDateTime expiredAt = LocalDateTime.now().minusHours(1);
        ConfirmToken confirmToken =
                new ConfirmToken(1L, token, LocalDateTime.now(), expiredAt, null, new AppUser());

        when(tokenInterface.getToken(token)).thenReturn(Optional.of(confirmToken));

        IllegalStateException exception =
                assertThrows(
                        IllegalStateException.class, () -> registrationService.confirmToken(token));

        assertEquals("Token is already expired!", exception.getMessage());
        verify(tokenInterface, times(1)).getToken(token);
    }

    @Test
    @DisplayName(
            "Should confirm the token and enable the user when the token is valid and not expired")
    void confirmTokenWhenTokenIsValidAndNotExpired() {
        String token = "valid-token";
        AppUser appUser = new AppUser();
        appUser.setEmail("test@example.com");
        appUser.setId(1L);

        ConfirmToken confirmToken = new ConfirmToken();
        confirmToken.setToken(token);
        confirmToken.setAppUser(appUser);
        confirmToken.setExpiresAt(LocalDateTime.now().plusMinutes(15));

        when(tokenInterface.getToken(token)).thenReturn(Optional.of(confirmToken));

        String result = registrationService.confirmToken(token);

        assertEquals("Your email is confirmed. Thank you for using our service!", result);
        verify(tokenInterface).setConfirmedAt(token);
        verify(userInterface).enableAppUser(appUser.getEmail(), 1);
        verify(userInterface).activeUser(appUser.getId());
    }

    @Test
    @DisplayName("Should not register a user with an invalid phone number")
    void notRegisterUserWithInvalidPhoneNumber() {
        RegistrationRequest request =
                new RegistrationRequest(
                        "testUsername",
                        "test@example.com",
                        "testPassword",
                        "Test Fullname",
                        "invalid_phone_number");

        when(userInterface.signUpUser(any(AppUser.class)))
                .thenThrow(new IllegalArgumentException("Invalid phone number"));

        assertThrows(IllegalArgumentException.class, () -> registrationService.register(request));

        verify(userInterface, times(1)).signUpUser(any(AppUser.class));
        verify(emailSender, times(0)).sendEmail(anyString(), anyString());
    }

    @Test
    @DisplayName("Should register a new user and send a confirmation email")
    void registerNewUserAndSendConfirmationEmail() {
        RegistrationRequest request =
                new RegistrationRequest(
                        "testUsername",
                        "test@example.com",
                        "testPassword",
                        "Test Fullname",
                        "1234567890");
        AppUser appUser =
                new AppUser(
                        "testUsername",
                        "test@example.com",
                        "testPassword",
                        "Test Fullname",
                        "1234567890",
                        "2022/01/01 00:00:00",
                        0,
                        new HashSet<>());
        String tokenForNewUser = "testToken";
        String registrationEmail = "Registration email content";
        String link = "http://localhost:3000/confirm/" + tokenForNewUser;

        when(userInterface.signUpUser(any(AppUser.class))).thenReturn(tokenForNewUser);
        when(buildEmail.registrationEmail(request.getEmail(), link)).thenReturn(registrationEmail);

        String result = registrationService.register(request);

        assertEquals(tokenForNewUser, result);
        verify(userInterface, times(1)).signUpUser(any(AppUser.class));
        verify(buildEmail, times(1)).registrationEmail(request.getEmail(), link);
        verify(emailSender, times(1)).sendEmail(request.getEmail(), registrationEmail);
    }
}