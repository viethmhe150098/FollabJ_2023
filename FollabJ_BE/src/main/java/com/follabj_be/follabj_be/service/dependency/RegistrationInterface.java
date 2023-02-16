package com.follabj_be.follabj_be.service.dependency;

import com.follabj_be.follabj_be.dto.RegistrationRequest;

public interface RegistrationInterface {
    String register(RegistrationRequest request);
    String confirmToken(String token);
    String buildEmail(String name, String link);
}
