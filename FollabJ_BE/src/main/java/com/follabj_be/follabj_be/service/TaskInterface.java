package com.follabj_be.follabj_be.service;

import com.follabj_be.follabj_be.entity.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskInterface {
    List<Task> getAllTasks() ;

    List<Task> getTasksByProjectId(Long projectId) ;

    Task addTask(Task task) ;

    Task updateTask(Long id, Task task);

    void deleteTask(Long taskId);

    void addAssigneeToTask(Long task_id, Long assignee_id);

    void removeAssigneeFromTask(Long task_id, Long assignee_id);

}
