package com.follabj_be.follabj_be.requestModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskRequest {
    private Long task_status_id;
    private String task_title;
    private String assignee;
}
