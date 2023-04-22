package com.follabj_be.follabj_be.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegistrationRequest {
    @Pattern(regexp = "^\s*[a-zA-Z0-9]{5,30}\s*$", message = "Not match pattern")
    @NotEmpty(message = "Not empty")
    private String username;
    @Email(message = "Not match pattern")
    @Size(min = 2)
    @Size(max = 30)
    @NotEmpty(message = "Not empty")
    private String email;
    @Pattern(regexp = "^(?=.*\\d)[a-zA-Z0-9]{8,}$", message = "Not match pattern")
    @NotEmpty(message = "Not empty")
    private String password;
    private String fullname;
    private String phone_number;
}
