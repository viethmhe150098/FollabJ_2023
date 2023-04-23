package com.follabj_be.follabj_be.dto;

import com.follabj_be.follabj_be.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
    private Long id;
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;
    private String username;
    private String fullname;
    private String phone_number;
    private int status;
    public Set<Role> roles;
    public UserDTO(Long id, String email, String username) {
        this.id = id;
        this.email = email;
        this.username = username;
    }

    public UserDTO(Long id, String email, String username, String fullname, String phone_number, int status) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.fullname = fullname;
        this.phone_number = phone_number;
        this.status = status;
    }
}
