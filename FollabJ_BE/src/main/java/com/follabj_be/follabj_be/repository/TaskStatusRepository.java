package com.follabj_be.follabj_be.repository;

import com.follabj_be.follabj_be.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long> {
    TaskStatus getTaskStatusById(Long id);
}
