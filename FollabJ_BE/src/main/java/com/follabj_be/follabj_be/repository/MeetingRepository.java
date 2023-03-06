package com.follabj_be.follabj_be.repository;

import com.follabj_be.follabj_be.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    @Query(value = "Select m from Meeting m where m.project_id.id=?1")
    List<Meeting> findByProject_id(Long p_id);
}
