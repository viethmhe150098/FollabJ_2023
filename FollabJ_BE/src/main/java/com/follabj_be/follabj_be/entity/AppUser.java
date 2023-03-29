package com.follabj_be.follabj_be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import junit.runner.Version;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AppUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String email;
    private String password;

    private String fullname;
    private String phone_number;
    private String createdAt;
    private int status;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    public Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "leader", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Project> leaded_project;

    @ManyToMany(mappedBy = "members")
    @JsonIgnore
    private Set<Project> projects;

    public AppUser(String username, String email, String password, int status, Set<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.status = status;
        this.roles = roles;
    }

    public AppUser(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public AppUser(String username, String email, String password, String fullname, String phone_number, String createdAt, int status, Set<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.phone_number = phone_number;
        this.createdAt = createdAt;
        this.status = status;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role r : roles
        ) {
            authorities.add(new SimpleGrantedAuthority(r.getName()));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
