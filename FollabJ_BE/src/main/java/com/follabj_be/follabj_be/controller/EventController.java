package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.dto.CreateEventDTO;
import com.follabj_be.follabj_be.dto.EventDTO;
import com.follabj_be.follabj_be.entity.Event;
import com.follabj_be.follabj_be.entity.Project;
import com.follabj_be.follabj_be.service.impl.EventService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin
public class EventController {

    @Autowired
    final EventService eventService;
    private ModelMapper modelMapper;

    @GetMapping("/project/{project_id}/events")
    public List<EventDTO> getEventsByProjectId(@PathVariable Long project_id) {
        List<Event> eventList = eventService.getEventsByProjectId(project_id);

        List<EventDTO> eventDTOList = new ArrayList<>();

        for (Event event : eventList) {
            EventDTO eventDTO = modelMapper.map(event, EventDTO.class);
            eventDTOList.add(eventDTO);
        }

        return eventDTOList;
    }

    @GetMapping("/events")
    public List<EventDTO> getEventsByUserId(@RequestParam Long user_id) {
        List<Event> eventList = eventService.getEventsByUserId(user_id);

        List<EventDTO> eventDTOList = new ArrayList<>();

        for (Event event : eventList) {
            EventDTO eventDTO = modelMapper.map(event, EventDTO.class);
            eventDTOList.add(eventDTO);
        }

        return eventDTOList;
    }

    @PostMapping("/project/{project_id}/leader/events")
    @PreAuthorize("hasAuthority('LEADER')")
    public EventDTO addEvent(@RequestBody CreateEventDTO createEventDTO, @PathVariable Long project_id) {
        createEventDTO.setProjectId(project_id);
        Event event = eventService.addEvent(createEventDTO);
        EventDTO eventDTO = modelMapper.map(event, EventDTO.class);
        return eventDTO;
    }

    @GetMapping("/project/{project_id}/events/{event_id}")
    public EventDTO getEventById(@PathVariable Long event_id) {
        Optional<Event> optionalEvent = eventService.getEventById(event_id);
        return optionalEvent.map(event -> modelMapper.map(event, EventDTO.class)).orElse(null);

    }

    @RequestMapping(
            method = RequestMethod.PUT,
            path = "/project/{project_id}/leader/events/{event_id}/update"
    )
    @PreAuthorize("hasAuthority('LEADER')")
    public EventDTO updateEvent(@RequestBody Event event, @PathVariable Long project_id, @PathVariable Long event_id) {
        event.setProject(new Project());
        event.getProject().setId(project_id);
        Event updatedEvent = eventService.updateEvent(event_id, event);

        EventDTO eventDTO = modelMapper.map(updatedEvent, EventDTO.class);
        return eventDTO;
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "/project/{project_id}/leader/events/{event_id}/delete"
    )
    @PreAuthorize("hasAuthority('LEADER')")
    public void deleteEvent(@PathVariable Long event_id) {
        eventService.deleteEvent(event_id);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            path = "/project/{project_id}/leader/events/{event_id}/add"
    )
    @PreAuthorize("hasAuthority('LEADER')")
    public void addParticipantToEvent(@PathVariable Long event_id, @RequestParam Long participant_id) {
        eventService.addParticipantToEvent(event_id, participant_id);
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "/project/{project_id}/leader/events/{event_id}/remove"
    )
    @PreAuthorize("hasAuthority('LEADER')")
    public void removeAssigneeFromTask(@PathVariable Long event_id, @RequestParam Long participant_id) {
        eventService.removeParticipantFromEvent(event_id, participant_id);
    }
}
