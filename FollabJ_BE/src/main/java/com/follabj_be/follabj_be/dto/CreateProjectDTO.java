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
    @NotEmpty
    @Size(min=1, max=50)
    private String p_name;
    @NotEmpty
    @Size(min=1, max=100)
    private String p_des;

}
