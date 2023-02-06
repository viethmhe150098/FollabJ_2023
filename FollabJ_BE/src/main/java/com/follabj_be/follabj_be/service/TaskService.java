package com.follabj_be.follabj_be.service;

import com.follabj_be.follabj_be.entity.Task;
import com.follabj_be.follabj_be.entity.TaskStatus;
import com.follabj_be.follabj_be.repository.TaskRepository;
import com.follabj_be.follabj_be.repository.TaskStatusRepository;
import com.follabj_be.follabj_be.service.dependency.TaskInterface;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TaskService implements TaskInterface {
    private final TaskRepository taskRepository;
    private final TaskStatusRepository taskStatusRepository;
    public TaskService(TaskRepository taskRepository, TaskStatusRepository taskStatusRepository) {
        this.taskRepository = taskRepository;
        this.taskStatusRepository = taskStatusRepository;
    }

    @Override
    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }

    @Override
    public Task insertTask(Task task) {
        return taskRepository.save(task);
    }

    public List<TaskStatus> getAllTaskStatus(){
        return taskStatusRepository.findAll();
    }
}
