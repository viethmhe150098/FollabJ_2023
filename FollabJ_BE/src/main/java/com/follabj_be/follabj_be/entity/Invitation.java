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
@Getter
@Setter
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    @JsonIgnore
    private AppUser receiver;
    @Column(columnDefinition = "integer default 0")
    private int status;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

}
