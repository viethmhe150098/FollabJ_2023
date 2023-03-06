package com.follabj_be.follabj_be.service;

import com.follabj_be.follabj_be.entity.ConfirmToken;

import java.util.Optional;

public interface TokenInterface {
    void saveConfirmationToken(ConfirmToken token);
    Optional<ConfirmToken> getToken(String token);
    int setConfirmedAt(String token);
}
