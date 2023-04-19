package com.follabj_be.follabj_be.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class BuildEmailTest {

    @InjectMocks
    BuildEmail buildEmail;

    @Test
    @DisplayName("Should return the correct email content when a user becomes a leader")
    void becomeLeaderReturnsCorrectEmailContent() {
        String name = "John Doe";

        String emailContent = buildEmail.becomeLeader(name);

        assertTrue(emailContent.contains("Dear " + name));
        assertTrue(
                emailContent.contains(
                        "You have become a team leader, from now on you can create and manage your group"));
        assertTrue(emailContent.contains("Best regard, FollabJ Team"));
    }

    @Test
    @DisplayName(
            "Should return the correct forgot password email template with the given password and name")
    void forgotPasswordReturnsCorrectEmailTemplate() {
        String password = "newPassword123";
        String name = "John Doe";

        String emailTemplate = buildEmail.forgotPassword(password, name);

        assertTrue(emailTemplate.contains("Hi " + name + ","));
        assertTrue(
                emailTemplate.contains(
                        "We wanted to let you know that your FollabJ password was reset to ."));
        assertTrue(emailTemplate.contains(password));
        assertTrue(emailTemplate.contains("Link will expire in 15 minutes."));
        assertTrue(emailTemplate.contains("See you soon"));
    }

    @Test
    @DisplayName("Should return a registration email with the given name and link")
    void registrationEmailWithNameAndLink() {
        String name = "John Doe";
        String link = "https://example.com/activate";

        String result = buildEmail.registrationEmail(name, link);

        assertTrue(result.contains(name), "The email should contain the given name");
        assertTrue(result.contains(link), "The email should contain the given link");
    }
}