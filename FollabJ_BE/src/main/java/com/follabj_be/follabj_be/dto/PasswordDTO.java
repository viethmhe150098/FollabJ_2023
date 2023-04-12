package com.follabj_be.follabj_be.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PasswordDTO {
    private int req_u_id;
    @Min(8)
    @Pattern(regexp = "^(?=.*\\d)[a-zA-Z0-9]{8,}$")
    private String new_password;
    @Min(8)
    @Pattern(regexp = "^(?=.*\\d)[a-zA-Z0-9]{8,}$")
    private String old_password;
}
