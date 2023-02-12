package com.follabj_be.follabj_be.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    Long id;
    @Column
    String title;

    @Column
    String assignee;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "task_status_id")
    @JsonBackReference
    public TaskStatus status;

    public Task(String title, String assignee, TaskStatus status) {
        this.title = title;
        this.assignee = assignee;
        this.status = status;
    }
}
