package com.follabj_be.follabj_be.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class RegistrationRequest {
    private String username;
    private String email;
    private String password;
}
