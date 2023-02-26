package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.dto.CreateEventDTO;
import com.follabj_be.follabj_be.dto.EventDTO;
import com.follabj_be.follabj_be.entity.Event;
import com.follabj_be.follabj_be.entity.Project;
import com.follabj_be.follabj_be.service.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class EventController {

    @Autowired
    EventService eventService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/project/{project_id}/events")
    public List<EventDTO> getEventsByProjectId(@PathVariable Long project_id) {
        List<Event> eventList = eventService.getEventsByProjectId(project_id);

        List<EventDTO> eventDTOList = new ArrayList<>();

        for(Event event: eventList) {
            EventDTO eventDTO = modelMapper.map(event, EventDTO.class);
            eventDTOList.add(eventDTO);
        }

        return eventDTOList;
    }

    @PostMapping("/project/{project_id}/events")
    public Event addEvent(@RequestBody CreateEventDTO createEventDTO, @PathVariable Long project_id) {
        createEventDTO.setProjectId(project_id);
        return eventService.addEvent(createEventDTO);
    }

    @GetMapping("/project/{project_id}/events/{event_id}")
    public EventDTO getEventById(@PathVariable Long event_id) {
        Optional<Event> optionalEvent = eventService.getEventById(event_id);
        if (optionalEvent.isPresent()) {
            EventDTO eventDTO = modelMapper.map(optionalEvent.get(), EventDTO.class);
            return eventDTO;
        }

        return null;
    }

    @RequestMapping(
            method=RequestMethod.PUT,
            path = "/project/{project_id}/events/{event_id}/update"
    )
    public Event updateEvent(@RequestBody Event event,@PathVariable Long project_id,@PathVariable Long event_id) {
        event.setProject(new Project());
        event.getProject().setId(project_id);
        return eventService.updateEvent(event_id, event);
    }

    @RequestMapping(
            method=RequestMethod.DELETE,
            path = "/project/{project_id}/events/{event_id}/delete"
    )
    public void deleteEvent(@PathVariable Long event_id) {
        eventService.deleteEvent(event_id);
    }

    @RequestMapping(
            method=RequestMethod.POST,
            path = "/project/{project_id}/events/{event_id}/add"
    )
    public void addParticipantToEvent(@PathVariable Long event_id, @RequestParam Long participant_id) {
        eventService.addParticipantToEvent(event_id, participant_id);
    }

    @RequestMapping(
            method=RequestMethod.DELETE,
            path = "/project/{project_id}/events/{event_id}/remove"
    )
    public void removeAssigneeFromTask(@PathVariable Long event_id, @RequestParam Long participant_id) {
        eventService.removeParticipantFromEvent(event_id, participant_id);
    }
}
