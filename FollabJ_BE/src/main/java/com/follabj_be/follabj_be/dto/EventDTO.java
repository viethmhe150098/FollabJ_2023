package com.follabj_be.follabj_be.dto;

import com.follabj_be.follabj_be.entity.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventDTO {

    private String title;

    private String description;

    private Date startDate;

    private Date endDate;

    private Project project;
}
