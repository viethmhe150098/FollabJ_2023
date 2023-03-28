package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.dto.EventDTO;
import com.follabj_be.follabj_be.entity.Event;
import com.follabj_be.follabj_be.entity.Project;
import com.follabj_be.follabj_be.service.impl.EventService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("EventController")
class EventControllerTest {

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController eventController;

    @Test
    @DisplayName("should return an empty list when no events are found for the given project ID")
    void getEventsByProjectIdReturnsEmptyListWhenNoEventsFound() {
        when(eventService.getEventsByProjectId(anyLong())).thenReturn(Collections.emptyList());

        List<EventDTO> events = eventController.getEventsByProjectId(1L);

        assertThat(events).isEmpty();
    }

    @Test
    @DisplayName("should return a list of EventDTOs for the given project ID")
    void getEventsByProjectIdReturnsListOfEventDTOs() {
        Project project = new Project();
        project.setId(1L);
        Event event = new Event();
        event.setId(1L);
        event.setTitle("title");
        event.setDescription("description");
        event.setStartDate(new Date(11,11, 2011));
        event.setEndDate(new Date(11,11, 2011));
        event.setProject(project);
        List<Event> eventList = new ArrayList<>();
        eventList.add(event);
        when(eventService.getEventsByProjectId(anyLong())).thenReturn(eventList);

        List<EventDTO> eventDTOList = eventController.getEventsByProjectId(1L);

        assertThat(eventDTOList).isNotNull();
        assertThat(eventDTOList.size()).isEqualTo(1);
        assertThat(eventDTOList.get(0).getId()).isEqualTo(1L);
        assertThat(eventDTOList.get(0).getTitle()).isEqualTo("title");
        assertThat(eventDTOList.get(0).getDescription()).isEqualTo("description");
        assertThat(eventDTOList.get(0).getStartDate()).isEqualTo(new Date(11,11, 2011));
        assertThat(eventDTOList.get(0).getEndDate()).isEqualTo(new Date(11,11, 2011));
        assertThat(eventDTOList.get(0).getProject().getId()).isEqualTo(1L);
    }
}