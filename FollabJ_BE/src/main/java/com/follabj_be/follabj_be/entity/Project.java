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
    public enum ProjectStatus {
        ACTIVE,
        DEACTIVATE
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String des;
    @Column(nullable = false)
    private String createdDate;

    @Column(columnDefinition = "varchar(255) default 'ACTIVE'")
    @Enumerated(value = EnumType.STRING)
    private ProjectStatus status = ProjectStatus.ACTIVE;
    @ManyToOne
    @JoinColumn(
            name = "leader_id"
    )
    private AppUser leader;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "project_members",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<AppUser> members;

    public Project(String name, String des, String createdDate, AppUser leader, Set<AppUser> members) {
        this.name = name;
        this.des = des;
        this.createdDate = createdDate;
        this.leader = leader;
        this.members = members;
    }

    public Project(Long id, String createdDate, ProjectStatus status, AppUser leader) {
        this.id = id;
        this.createdDate = createdDate;
        this.status = status;
        this.leader = leader;
    }
}
