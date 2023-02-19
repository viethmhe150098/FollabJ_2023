package com.follabj_be.follabj_be.service.dependency;

import com.follabj_be.follabj_be.entity.Event;

import java.util.List;
import java.util.Optional;

public interface EventInterface {

    List<Event> getEventsByProjectId(Long project_id);

    Optional<Event> getEventById(Long event_id);

    Event addEvent(Event event);

    Event updateEvent(Long event_id, Event event);

    void deleteEvent(Long event_id);
}
