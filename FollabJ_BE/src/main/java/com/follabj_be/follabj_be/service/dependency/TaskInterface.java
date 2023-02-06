package com.follabj_be.follabj_be.service.dependency;

import com.follabj_be.follabj_be.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface TaskInterface {
    List<Task> getAllTask();
    Task insertTask(Task task);
}
