package com.follabj_be.follabj_be.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
//    @OneToOne(cascade = CascadeType.ALL, mappedBy = "project_lead")
//    @JoinColumn(name = "user_id")
//    private AppUser leader;
    private String project_name;
    private String project_des;

//    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "projects")
//    public List<AppUser> member_id;

    @OneToOne(mappedBy = "project")
    public AppUser user;
}
