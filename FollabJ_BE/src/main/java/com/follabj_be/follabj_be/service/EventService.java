package com.follabj_be.follabj_be.service;

import com.follabj_be.follabj_be.entity.Event;
import com.follabj_be.follabj_be.repository.EventRepository;
import com.follabj_be.follabj_be.service.dependency.EventInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class EventService implements EventInterface {

    @Autowired
    EventRepository eventRepository;
    @Override
    public List<Event> getEventsByProjectId(Long project_id) {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long event_id) {
        return eventRepository.findById(event_id);
    }

    @Override
    public Event addEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(Long event_id, Event event) {
        event.setId(event_id);
        return eventRepository.save(event);
    }

    @Override
    public void deleteEvent(Long event_id) {
        eventRepository.deleteById(event_id);
    }
}
