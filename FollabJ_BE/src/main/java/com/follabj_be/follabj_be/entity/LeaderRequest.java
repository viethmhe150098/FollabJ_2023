package com.follabj_be.follabj_be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter
public class LeaderRequest {
    public enum requestStatus {
        PENDING,
        ACCEPT,
        REJECT
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "u_id", referencedColumnName = "id")
    @JsonIgnore
    private AppUser user;
    private String user_fullname;
    private String user_id_number;
    @Column(columnDefinition = "default 0")
    private requestStatus status;

    public LeaderRequest(AppUser user, String user_fullname, String user_id_number) {
        this.user = user;
        this.user_fullname = user_fullname;
        this.user_id_number = user_id_number;
    }
}
