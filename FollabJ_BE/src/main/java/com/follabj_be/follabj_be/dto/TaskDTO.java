package com.follabj_be.follabj_be.dto;

import lombok.*;

import javax.validation.constraints.Max;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskDTO {
    private Long id;
    @Max(30)
    private String title;
    @Max(100)
    private String description;

    private String label;

    private Date startDate;

    private Date endDate;

    private int statusId;

    private int columnPosition;
    @NonNull
    private List<UserDTO> assigneeList;

}
