package com.follabj_be.follabj_be.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CreateEventDTO {
    @NotNull
    @Max(30)
    private String title;
    @NotNull
    @Max(100)
    private String description;

    private Date startDate;

    private Date endDate;

    private Long projectId;
    @NotNull
    private List<UserDTO> participantList;
}
