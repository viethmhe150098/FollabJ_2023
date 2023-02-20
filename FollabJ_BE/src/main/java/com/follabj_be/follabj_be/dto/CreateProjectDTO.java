package com.follabj_be.follabj_be.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CreateProjectDTO {
    private String user_id;
    private String p_name;
    private String p_des;
}
