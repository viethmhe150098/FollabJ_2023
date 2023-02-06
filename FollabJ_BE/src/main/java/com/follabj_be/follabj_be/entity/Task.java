package com.follabj_be.follabj_be.entity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


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
    Long taskid;
    @Column
    String title;
    @Column
    String description;
    @Column
    TaskStatus taskStatus;
}
