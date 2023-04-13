package com.follabj_be.follabj_be.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CreateProjectDTO {
    private String id;

    @NotEmpty(message = "Not empty")
    @Max(value = 30, message = "Not match pattern")
    @Min(value = 1,message = "Not match pattern")
    private String p_name;
    @NotEmpty(message = "Not empty")
    @Max(value = 100, message = "Not match pattern")
    @Min(value = 1,message = "Not match pattern")
    private String p_des;

}
