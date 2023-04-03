package com.follabj_be.follabj_be.service.impl;

import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.AppUserRole;
import com.follabj_be.follabj_be.entity.ConfirmToken;
import com.follabj_be.follabj_be.entity.Role;
import com.follabj_be.follabj_be.service.EmailSender;
import com.follabj_be.follabj_be.dto.RegistrationRequest;
import com.follabj_be.follabj_be.service.RegistrationInterface;
import com.follabj_be.follabj_be.service.TokenInterface;
import com.follabj_be.follabj_be.service.UserInterface;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class RegistrationService implements RegistrationInterface {
    private final UserInterface userInterface;
    private final TokenInterface tokenInterface;
    private final EmailSender emailSender;
    private final BuildEmail buildEmail;

    public RegistrationService(UserInterface userInterface, TokenInterface tokenInterface, EmailSender emailSender, BuildEmail buildEmail) {
        this.userInterface = userInterface;
        this.tokenInterface = tokenInterface;
        this.emailSender = emailSender;
        this.buildEmail = buildEmail;
    }

    @Override
    public String register(RegistrationRequest request) {
        Set<Role> roles = new HashSet<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        roles.add(new Role(0L, AppUserRole.INACTIVE_USER.toString()));
        String email = request.getEmail();
        String username = request.getEmail();
        String phone_number = request.getPhone_number();
        String password = request.getPassword();
        String createdAt = dtf.format(now);
        String fullname = request.getFullname();
        String tokenForNewUser = userInterface.signUpUser(new AppUser(
                    username,
                email,
                password,
                fullname,
                phone_number,
                createdAt,
                0,
                roles
                    ));

            //Since, we are running the spring boot application in localhost, we are hardcoding the
            //url of the server. We are creating a POST request with token param
            String link = "http://localhost:8080/confirm?token=" + tokenForNewUser;
            emailSender.sendEmail(email, buildEmail.registrationEmail(email, link));
            return tokenForNewUser;
    }

    @Override
    @Transactional
    public String confirmToken(String token) {
        Optional<ConfirmToken> confirmToken = tokenInterface.getToken(token);

        if (confirmToken.isEmpty()) {
            throw new IllegalStateException("Token not found!");
        }

        if (confirmToken.get().getConfirmedAt() != null) {
            throw new IllegalStateException("Email is already confirmed");
        }

        LocalDateTime expiresAt = confirmToken.get().getExpiresAt();

        if (expiresAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token is already expired!");
        }

        tokenInterface.setConfirmedAt(token);
        userInterface.enableAppUser(confirmToken.get().getAppUser().getEmail(), 1);
        userInterface.activeUser(confirmToken.get().getAppUser().getId());
        //Returning confirmation message if the token matches
        return "Your email is confirmed. Thank you for using our service!";
    }

    @Override
    public String buildEmail(String name, String link) {
        return null;
    }


}
