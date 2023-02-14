package com.follabj_be.follabj_be.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_id")
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "project_id")
    private AppUser leader_id;
    private String project_name;
    private String project_des;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "ProjectMembers",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<AppUser> member_id;

}
