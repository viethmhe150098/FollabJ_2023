package com.follabj_be.follabj_be.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class TaskStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "task_status_id")
    private Long id;
    private String title;
    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL)
    @JsonManagedReference
    public List<Task> tasks;
}
