package com.follabj_be.follabj_be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.follabj_be.follabj_be.dto.UserDTO;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String description;

    private String label;

    private Date startDate;

    private Date endDate;
    @Column(columnDefinition = "integer default 1")
    private int statusId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="reporter_id",referencedColumnName = "id")
    private AppUser reporter;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="task_assignee",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<AppUser> assigneeList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="project_id",referencedColumnName = "id")
    private Project project;
}
