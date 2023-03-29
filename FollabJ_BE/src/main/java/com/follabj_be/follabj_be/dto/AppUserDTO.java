package com.follabj_be.follabj_be.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AppUserDTO {
    private Long id;
    private String email;
    private String username;
    private int status;
    private String fullname;

    private String phone_number;

    public AppUserDTO(Long id, String email, String username, int status) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.status = status;
    }
}
