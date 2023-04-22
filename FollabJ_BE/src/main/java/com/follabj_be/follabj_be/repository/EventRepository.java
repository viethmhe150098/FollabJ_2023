package com.follabj_be.follabj_be.repository;

import com.follabj_be.follabj_be.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    
    List<Event> findByParticipantListId(Long user_id);

    @Query(value = "Select e from Event e where e.project.id=?1")
    List<Event> findByProjectId(Long p_id);

    Event findByIdAndProjectId(Long id, Long project_id);

    void deleteByIdAndParticipantListId(Long id, Long participant_id);
}
