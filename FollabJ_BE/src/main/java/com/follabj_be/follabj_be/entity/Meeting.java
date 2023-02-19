package com.follabj_be.follabj_be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter
public class Meeting {
    @Id
    private String id;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project_id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private MeetingType meetingType;

}
