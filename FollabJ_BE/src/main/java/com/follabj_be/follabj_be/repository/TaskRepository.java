package com.follabj_be.follabj_be.repository;

import com.follabj_be.follabj_be.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProjectId(Long id);
    @Transactional
    @Modifying
    @Query(value = "update task set status_id=?2 where id=?1", nativeQuery = true)
    void updateTaskStatus(Long taskId, int statusId);

    @Transactional
    @Modifying
    @Query(value = "update task set column_position=?2 where id=?1", nativeQuery = true)
    void updateTaskColumnPosition(Long taskId, int columnPosition);

    @Transactional
    @Modifying
    @Query(value = "update task set column_position= column_position+1 where project_id=?1 and status_id=(select status_id from (SELECT * FROM task) as status where id=?2) and column_position >= (select column_position from (SELECT * FROM task) as position where id=?2) and id != ?2", nativeQuery = true)
    void updateDestinationColumnPositionWhenChangeStatus(Long projectId, Long task_id);

    @Transactional
    @Modifying
    @Query(value = "update task set column_position= column_position-1 where project_id=?1 and status_id=(select status_id from (SELECT * FROM task) as status where id=?2) and column_position >= (select column_position from (SELECT * FROM task) as position where id=?2) and id != ?2", nativeQuery = true)
    void updateSourceColumnPositionWhenChangeStatus(Long projectId, Long task_id);

    @Transactional
    @Modifying
    @Query(value = "update task set column_position= column_position-1 where project_id=?1 and status_id=(select status_id from (SELECT * FROM task) as status where id=?2) and column_position <= (select column_position from (SELECT * FROM task) as position where id=?2) and id != ?2", nativeQuery = true)
    void updateColumnPositionAfterChangeTaskPosition(Long projectId, Long task_id);
}
