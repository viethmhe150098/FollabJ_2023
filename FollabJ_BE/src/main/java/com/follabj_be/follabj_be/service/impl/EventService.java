package com.follabj_be.follabj_be.service.impl;

import com.follabj_be.follabj_be.dto.CreateEventDTO;
import com.follabj_be.follabj_be.dto.EventDTO;
import com.follabj_be.follabj_be.dto.UserDTO;
import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.Event;
import com.follabj_be.follabj_be.entity.Project;
import com.follabj_be.follabj_be.repository.EventParticipantRepository;
import com.follabj_be.follabj_be.repository.EventRepository;
import com.follabj_be.follabj_be.repository.ProjectRepository;
import com.follabj_be.follabj_be.repository.UserRepository;
import com.follabj_be.follabj_be.service.EventInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventService implements EventInterface {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    EventParticipantRepository eventParticipantRepository;
    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Event> getEventsByProjectId(Long project_id) {
        return eventRepository.findAll();
    }

    @Override
    public List<Event> getEventsByUserId(Long user_id) {
        return eventRepository.findByParticipantListId(user_id);
    }

    @Override
    public Optional<Event> getEventById(Long event_id) {
        return eventRepository.findById(event_id);
    }

    @Override
    public Event addEvent(CreateEventDTO createEventDTO) {
        Event event = new Event();

        event.setTitle(createEventDTO.getTitle());
        event.setDescription(createEventDTO.getDescription());
        event.setStartDate(createEventDTO.getStartDate());
        event.setEndDate(createEventDTO.getEndDate());

        Optional<Project> optionalProject = projectRepository.findById(createEventDTO.getProjectId());

        if(optionalProject.isPresent()) {
            event.setProject(optionalProject.get());
        }

        List<AppUser> userList = new ArrayList<>();

        for (UserDTO userDTO : createEventDTO.getParticipantList()) {

            Optional<AppUser> optionalUser = userRepository.findById(userDTO.getId());
            if(optionalUser.isPresent()) {
                userList.add(optionalUser.get());
            }
        }

        event.setParticipantList(userList);

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

    @Override
    public void addParticipantToEvent(Long event_id, Long participant_id) {
        eventParticipantRepository.addParticipantToEvent(event_id, participant_id);
    }

    @Override
    public void removeParticipantFromEvent(Long event_id, Long participant_id) {
        eventParticipantRepository.removeParticipantFromEvent(event_id, participant_id);
    }
}
