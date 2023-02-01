package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.entity.Task;
import com.follabj_be.follabj_be.service.TaskManagementService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskManagementController {
    private TaskManagementService taskManagementService;

    public TaskManagementController(TaskManagementService taskManagementService) {
        this.taskManagementService = taskManagementService;
    }

    @GetMapping()
    public ResponseEntity<List<Task>> GetAllTasks() {
        List<Task> taskList = taskManagementService.GetAllTask();
        return new ResponseEntity<>(taskList, HttpStatus.OK);
    }

    @GetMapping({"/{taskId}"})
    public ResponseEntity<Task> GetTaskByID(@PathVariable Long taskId) {
        return new ResponseEntity<>(taskManagementService.GetTaskByID(taskId), HttpStatus.OK);
    }

    public ResponseEntity<Task> InsertTask(@RequestBody Task taskInput) {
        Task taskOutput = taskManagementService.InsertTask(taskInput);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("task", "/api/v1/task/" + taskOutput.getTaskid().toString());
        return new ResponseEntity<>(taskOutput, httpHeaders, HttpStatus.CREATED);
    }
    @PutMapping({"/{taskId}"})
    public ResponseEntity<Task> UpdateTaskByID(@PathVariable("taskId") Long taskId, @RequestBody Task task) {
        taskManagementService.UpdateTask(taskId, task);
        return new ResponseEntity<>(taskManagementService.GetTaskByID(taskId), HttpStatus.OK);
    }

    @DeleteMapping({"/{taskId}"})
    public ResponseEntity<Task> DeleteTaskByID(@PathVariable("taskId") Long taskId) {
        taskManagementService.DeleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
