package com.follabj_be.follabj_be.service;

import com.follabj_be.follabj_be.entity.ConfirmToken;
import com.follabj_be.follabj_be.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TokenService {
    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository confirmationTokenRepository) {
        this.tokenRepository = confirmationTokenRepository;
    }

    public void saveConfirmationToken(ConfirmToken token) {
        tokenRepository.save(token);
    }

    public Optional<ConfirmToken> getToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return tokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }
}
