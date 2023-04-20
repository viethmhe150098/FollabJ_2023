package com.follabj_be.follabj_be.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskDTO {
    private Long id;
    @NotBlank(message = "Title is mandatory")
    @Size(max=30)
    private String title;
    @Size(max=100)
    private String description;

    @NotBlank(message = "Label is mandatory")
    @Size(max=50)
    private String label;

    private Date startDate;

    private Date endDate;

    private int statusId;

    @NotNull(message = "Position in column is mandatory")
    private int columnPosition;

    private List<UserDTO> assigneeList;

    private ProjectDTO project;

}
