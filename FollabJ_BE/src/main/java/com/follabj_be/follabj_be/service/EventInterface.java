package com.follabj_be.follabj_be.service;

import com.follabj_be.follabj_be.dto.CreateEventDTO;
import com.follabj_be.follabj_be.entity.Event;

import java.util.List;
import java.util.Optional;

public interface EventInterface {

    List<Event> getEventsByProjectId(Long project_id);

    List<Event> getEventsByUserId(Long user_id);

    Optional<Event> getEventById(Long event_id);

    Event addEvent(CreateEventDTO createEventDTO);

    Event updateEvent(Long event_id, Event event);

    void deleteEvent(Long event_id);

    void addParticipantToEvent(Long event_id, Long participant_id);

    void removeParticipantFromEvent(Long event_id, Long participant_id);

    boolean checkIfEventExistInProject(Long projectId, Long eventId);
}
