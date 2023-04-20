package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.dto.CreateEventDTO;
import com.follabj_be.follabj_be.dto.EventDTO;
import com.follabj_be.follabj_be.entity.Event;
import com.follabj_be.follabj_be.entity.Project;
import com.follabj_be.follabj_be.entity.Task;
import com.follabj_be.follabj_be.errorMessge.CustomErrorMessage;
import com.follabj_be.follabj_be.service.impl.EventService;
import com.follabj_be.follabj_be.service.impl.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin
public class EventController {

    @Autowired
    final EventService eventService;

    @Autowired
    UserService userService;
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
    public List<EventDTO> getEventsByUserId(Authentication authentication) {

        Long user_id = userService.getUserByEmail(authentication.getPrincipal().toString()).getId();

        List<Event> eventList = eventService.getEventsByUserId(user_id);

        List<EventDTO> eventDTOList = new ArrayList<>();

        for (Event event : eventList) {
            EventDTO eventDTO = modelMapper.map(event, EventDTO.class);
            eventDTOList.add(eventDTO);
        }

        return eventDTOList;
    }

    @PostMapping("/project/{project_id}/leader/events")
    public EventDTO addEvent(@Valid  @RequestBody CreateEventDTO createEventDTO, @PathVariable Long project_id) {
        createEventDTO.getParticipantList().forEach(member -> {
            if(userService.checkIfUserExistInProject(project_id, member.getId()) == 0)
                throw new RuntimeException("[participantList] " + CustomErrorMessage.NOT_TEAMMEMBER.getMessage());
        });
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
    public EventDTO updateEvent(@Valid @RequestBody Event event, @PathVariable Long project_id, @PathVariable Long event_id) {
        checkIfEventBelongToProject(event_id, project_id);
        checkIfParticipantAssigneeListValid(event, project_id);
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
    public void deleteEvent(@PathVariable Long project_id, @PathVariable Long event_id) {
        checkIfEventBelongToProject(event_id, project_id);
        eventService.deleteEvent(event_id)
        ;
    }

    public void checkIfEventBelongToProject(Long event_id, Long project_id) {
        if(!eventService.checkIfEventExistInProject(project_id, event_id)) {
            throw new RuntimeException("Event id not existed in project");
        }
    }

    public void checkIfParticipantAssigneeListValid(Event event, Long project_id) {
        event.getParticipantList().forEach(member -> {
            if(userService.checkIfUserExistInProject(project_id, member.getId()) == 0)
                throw new RuntimeException("[participantList] " +CustomErrorMessage.NOT_TEAMMEMBER.getMessage());
        });
    }
}
