package com.follabj_be.follabj_be.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateUserDTO {
    private Long u_id;
    private String fullname;
    private String username;
    private String phone_number;
}
