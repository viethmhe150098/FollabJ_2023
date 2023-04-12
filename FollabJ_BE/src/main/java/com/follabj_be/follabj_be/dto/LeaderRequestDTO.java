package com.follabj_be.follabj_be.dto;

import com.follabj_be.follabj_be.entity.LeaderRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class LeaderRequestDTO {
    private Long id;
    private String message;

    private UserDTO user;

    private LeaderRequest.requestStatus status;
}
