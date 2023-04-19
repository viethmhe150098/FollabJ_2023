package com.follabj_be.follabj_be.service.impl;

import com.follabj_be.follabj_be.entity.Task;
import com.follabj_be.follabj_be.repository.TaskAssigneeRepository;
import com.follabj_be.follabj_be.repository.TaskRepository;
import com.follabj_be.follabj_be.service.TaskInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService implements TaskInterface {
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskAssigneeRepository taskAssigneeRepository;

    @Override
    public List<Task> getAllTasks() {

        return taskRepository.findAll();
    }

    @Override
    public List<Task> getTasksByProjectId(Long projectId) {

        return taskRepository.findByProjectId(projectId);
    }

    public Optional<Task> getTaskById(Long taskId) {
        return taskRepository.findById(taskId);
    }

    @Override
    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long id, Task task) {
        task.setId(id);
        return taskRepository.save(task);
    }

    @Override
    public void updateTaskStatus(Long id, int status) {
        taskRepository.updateTaskStatus(id, status);
    }

    @Override
    public void updateTaskColumnPosition(Long id, int columnPosition) {
        taskRepository.updateTaskColumnPosition(id, columnPosition);
    }

    @Override
    public void updateColumnPositionAfterChangeTaskPosition(Long project_id, Long task_id) {
        taskRepository.updateColumnPositionAfterChangeTaskPosition(project_id, task_id);
    }

    @Override
    public void updateSourceColumnPositionBeforeChangeTaskStatus(Long project_id, Long task_id) {
        taskRepository.updateSourceColumnPositionWhenChangeStatus(project_id, task_id);
    }

    @Override
    public void updateDestinationColumnPositionAfterChangeTaskStatus(Long project_id, Long task_id) {
        taskRepository.updateDestinationColumnPositionWhenChangeStatus(project_id, task_id);
    }


    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public void addAssigneeToTask(Long task_id, Long assignee_id) {
        taskAssigneeRepository.addAssigneeToTask(task_id, assignee_id);
    }

    @Override
    public void removeAssigneeFromTask(Long task_id, Long assignee_id) {
        taskAssigneeRepository.removeAssigneeFromTask(task_id, assignee_id);
    }

}
