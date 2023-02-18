package com.follabj_be.follabj_be.service.dependency;

import com.follabj_be.follabj_be.dto.UserDTO;
import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.Invitation;

import java.util.List;

public interface UserInterface {
    String signUpUser(AppUser appUser);
    void saveConfirmationToken(AppUser appUser, String token);
    void enableAppUser(String email, int status);
    void activeUser(Long user_id);

    List<UserDTO> findUsersByEmail(String email_cha);

    List<Invitation> getAllInvitation(Long user_id);
}
