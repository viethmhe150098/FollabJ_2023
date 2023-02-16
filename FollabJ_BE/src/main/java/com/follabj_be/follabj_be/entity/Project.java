package com.follabj_be.follabj_be.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter @Setter
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
    private AppUser leader;

    @ManyToMany
    @JoinTable(
            name = "project_members",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<AppUser> members;

    public Project(String name, String des, String createdDate, AppUser leader) {
        this.name = name;
        this.des = des;
        this.createdDate = createdDate;
        this.leader = leader;
    }
}
