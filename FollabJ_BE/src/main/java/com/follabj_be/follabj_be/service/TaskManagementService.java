package com.follabj_be.follabj_be.service;

import com.follabj_be.follabj_be.entity.Task;
import com.follabj_be.follabj_be.repository.TaskManagementRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class TaskManagementService {
    private TaskManagementRepository taskManagementRepository;

    public TaskManagementService(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }
    public List<Task> GetAllTask(){
        List<Task> taskList = new ArrayList<>();
        taskManagementRepository.findAll().forEach(taskList :: add);
        return taskList;
    }

    public Task GetTaskByID(Long taskID) {
        return taskManagementRepository.findById(taskID).get();
    }

    public Task InsertTask(Task task) {
        return taskManagementRepository.save(task);
    }

    public void UpdateTask(Long id, Task task) {
        Task taskFromDB = taskManagementRepository.findById(id).get();
        System.out.println(taskFromDB.toString());
        taskFromDB.setTaskStatus(task.getTaskStatus());
        taskFromDB.setDescription(task.getDescription());
        taskFromDB.setTitle(task.getTitle());
        taskManagementRepository.save(taskFromDB);
    }

    public void DeleteTask(Long taskId) {
        taskManagementRepository.deleteById(taskId);
    }
}
