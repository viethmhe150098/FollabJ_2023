package com.follabj_be.follabj_be.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CreateProjectDTO {
    private String id;
    @Max(30)
    @NotNull
    private String p_name;
    private String p_des;

}
