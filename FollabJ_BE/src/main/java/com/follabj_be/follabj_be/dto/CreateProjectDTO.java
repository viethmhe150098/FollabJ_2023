package com.follabj_be.follabj_be.dto;

import lombok.*;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CreateProjectDTO {
    private String id;

    @Size(min=1, max=30)
    private String p_name;
    @Size(min=1, max=100)
    private String p_des;

}
