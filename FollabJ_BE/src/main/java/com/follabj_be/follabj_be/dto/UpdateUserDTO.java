package com.follabj_be.follabj_be.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateUserDTO {
    private Long u_id;
    @Pattern(regexp = "^\s*[a-zA-Z]{2,}(?:\\s+[a-zA-Z]+){1,4}\s*$", message = "Not match pattern")
    private String fullname;
    @Pattern(regexp = "^\s*[a-zA-Z0-9]{5,30}\s*$", message = "Not match pattern")
    private String username;
    @Pattern(regexp = "^0\\d{9}$", message = "Not match pattern")
    private String phone_number;
}
