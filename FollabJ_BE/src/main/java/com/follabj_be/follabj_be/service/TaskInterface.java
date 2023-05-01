package com.follabj_be.follabj_be.service;

import com.follabj_be.follabj_be.entity.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskInterface {
    List<Task> getAllTasks();

    List<Task> getTasksByProjectId(Long projectId);

    Task addTask(Task task);

    Task updateTask(Long id, Task task);

    void updateTaskStatus(Long id, int status);

    void updateTaskColumnPosition(Long id, int columnPosition);

    void updateColumnPositionWhenChangeTaskPositionDown(Long project_id, Long task_id, int columnPosition);

    void updateColumnPositionWhenChangeTaskPositionUp(Long project_id, Long task_id, int columnPosition);

    void updateSourceColumnPositionBeforeChangeTaskStatus(Long project_id, Long task_id);

    void updateDestinationColumnPositionAfterChangeTaskStatus(Long project_id, Long task_id);

    void deleteTask(Long taskId);

    void addAssigneeToTask(Long task_id, Long assignee_id);

    void removeAssigneeFromTask(Long task_id, Long assignee_id);

    boolean checkIfTaskExistInProject(Long project_id, Long task_id);
}
