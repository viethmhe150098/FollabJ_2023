package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.entity.Task;
import com.follabj_be.follabj_be.requestModel.TaskRequest;
import com.follabj_be.follabj_be.service.TaskService;
import com.follabj_be.follabj_be.service.TaskStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@PreAuthorize("hasAuthority('ACTIVE_USER')")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskStatusService taskStatusService;
    @PostMapping("/task")
    public ResponseEntity<Task> addTask(@RequestBody TaskRequest taskRequest){
        Long task_status_id = taskRequest.getTask_status_id();
        Task task = taskService.insertTask(new Task(taskRequest.getTask_title(), taskStatusService.getName(task_status_id)));
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @GetMapping("/task")
    public Map<Object, Object> getTaskStatus(){
        Map<Object , Object> response = new HashMap<>();
        response.put("list", taskService.getAllTaskStatus());
        response.put("status", HttpStatus.OK);
        return response;
    }
}
