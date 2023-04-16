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
    private AppUser user;
    @Column(nullable = false)
    private String message;

    @Column(columnDefinition = "varchar(255) default 'PENDING'")
    @Enumerated(value = EnumType.STRING)
    private requestStatus status = requestStatus.PENDING;

}
