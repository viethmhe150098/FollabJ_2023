package com.follabj_be.follabj_be.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MeetingType {
    @Id
    private Integer id;
    @Column(nullable = false, unique = true)
    private String type;


}
