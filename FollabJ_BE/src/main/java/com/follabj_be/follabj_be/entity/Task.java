package com.follabj_be.follabj_be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.follabj_be.follabj_be.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String description;

    private String label;

    private String category;

    private Date startDate;

    private Date endDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name="reporter_id",referencedColumnName = "id")
    private AppUser reporter;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="task_assignee",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<AppUser> assigneeList;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Project project;
}
