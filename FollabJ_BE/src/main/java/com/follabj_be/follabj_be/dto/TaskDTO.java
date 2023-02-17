package com.follabj_be.follabj_be.dto;

import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.Project;
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
public class TaskDTO {
    private Long id;

    private String title;

    private String description;

    private String label;

    private String category;

    private Date startDate;

    private Date endDate;

    private List<UserDTO> assigneeList;

}
