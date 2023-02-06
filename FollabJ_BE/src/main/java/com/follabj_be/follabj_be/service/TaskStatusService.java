package com.follabj_be.follabj_be.service;

import com.follabj_be.follabj_be.entity.TaskStatus;
import com.follabj_be.follabj_be.repository.TaskStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskStatusService {
    @Autowired
    private TaskStatusRepository taskStatusRepository;

    public TaskStatus getName(Long id){
        return taskStatusRepository.getTaskStatusById(id);
    }
}
