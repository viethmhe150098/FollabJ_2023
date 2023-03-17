package com.follabj_be.follabj_be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String des;
    private String createdDate;
    @ManyToOne
    @JoinColumn(
            name = "leader_id"
    )
    @JsonIgnore
    private AppUser leader;

    @ManyToMany
    @JoinTable(
            name = "project_members",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnore
    private Set<AppUser> members;

    @Column(columnDefinition = "integer default 1")
    private int status;

    public Project(String name, String des, String createdDate, AppUser leader, Set<AppUser> members) {
        this.name = name;
        this.des = des;
        this.createdDate = createdDate;
        this.leader = leader;
        this.members = members;
    }
}
