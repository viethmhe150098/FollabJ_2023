package com.follabj_be.follabj_be.repository;

import com.follabj_be.follabj_be.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface TaskAssigneeRepository extends JpaRepository<AppUser, Long> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "insert into task_assignee values(?1,?2)")
    int addAssigneeToTask(Long task_id, Long assignee_id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "delete from task_assignee where task_id=?1 and user_id=?2")
    int removeAssigneeFromTask(Long task_id, Long assignee_id);
}
