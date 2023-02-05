package com.follabj_be.follabj_be.service.dependency;

import com.follabj_be.follabj_be.entity.AppUser;

public interface UserInterface {
    String signUpUser(AppUser appUser);
    void saveConfirmationToken(AppUser appUser, String token);
    void enableAppUser(String email, int status);
    void activeUser(Long user_id);
}
