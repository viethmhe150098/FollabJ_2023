package com.follabj_be.follabj_be.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateEventDTO {

    private String title;

    private String description;

    private Date startDate;

    private Date endDate;

    private Long projectId;

    private List<UserDTO> participantList;
}
