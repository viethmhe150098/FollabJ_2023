package com.follabj_be.follabj_be.service;

import com.follabj_be.follabj_be.entity.Event;
import com.follabj_be.follabj_be.repository.EventRepository;
import com.follabj_be.follabj_be.service.dependency.EventInterface;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EventService implements EventInterface {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public List<Event> getAllEvent() {
        return eventRepository.findAll();
    }
}
