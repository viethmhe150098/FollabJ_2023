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
public class PasswordDTO {
    private int req_u_id;
    @NotEmpty(message = "Not empty")
    @Pattern(regexp = "^(?=.*\\d)[a-zA-Z0-9]{8,}$")
    private String new_password;
    @NotEmpty(message = "Not empty")
    private String old_password;
}
