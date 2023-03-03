package com.follabj_be.follabj_be.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CreateProjectDTO {
    private String id;
    private String p_name;
    private String p_des;

}
