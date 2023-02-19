package com.follabj_be.follabj_be.repository;

import com.follabj_be.follabj_be.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface EventParticipantRepository extends JpaRepository<Event, Long> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "insert into event_participant values(?1,?2)")
    int addParticipantToEvent(Long event_id, Long participant_id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "delete from event_participant where event_id=?1 and user_id=?2")
    int removeParticipantFromEvent(Long event_id, Long participant_id);
}
