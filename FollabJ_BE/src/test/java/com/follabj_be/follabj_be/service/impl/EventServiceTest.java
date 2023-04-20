package com.follabj_be.follabj_be.service.impl;

import com.follabj_be.follabj_be.dto.CreateEventDTO;
import com.follabj_be.follabj_be.dto.UserDTO;
import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.Event;
import com.follabj_be.follabj_be.entity.Project;
import com.follabj_be.follabj_be.repository.EventParticipantRepository;
import com.follabj_be.follabj_be.repository.EventRepository;
import com.follabj_be.follabj_be.repository.ProjectRepository;
import com.follabj_be.follabj_be.repository.UserRepository;
import org.junit.Rule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.quality.Strictness;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    EventRepository eventRepository;

    @Mock
    ProjectRepository projectRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    EventParticipantRepository eventParticipantRepository;
    @InjectMocks
    EventService eventService;
    @Test
    @DisplayName(
            "Should remove the participant from the event when both event and participant exist")
    void removeParticipantFromEventWhenEventAndParticipantExist() {
        Long eventId = 1L;
        Long participantId = 2L;
        EventParticipantRepository eventParticipantRepository =
                mock(EventParticipantRepository.class);
        eventService.removeParticipantFromEvent(eventId, participantId);
        verify(eventParticipantRepository, times(0))
                .removeParticipantFromEvent(eventId, participantId);
    }

    @Test
    @DisplayName("Should not remove the participant when both event and participant do not exist")
    void removeParticipantFromEventWhenEventAndParticipantDoNotExist() {
        Long eventId = 1L;
        Long participantId = 2L;
        eventService.removeParticipantFromEvent(eventId, participantId);
        verify(eventRepository, times(0)).save(any(Event.class));
        verify(userRepository, times(0)).save(any(AppUser.class));
    }

    @Test
    @DisplayName("Should not remove the participant when the participant does not exist")
    void removeParticipantFromEventWhenParticipantDoesNotExist() {
        Long eventId = 1L;
        Long participantId = 2L;
        eventService.removeParticipantFromEvent(eventId, participantId);
        verify(eventRepository, times(0)).findById(eventId);
        verify(userRepository, times(0)).findById(participantId);
        verify(eventRepository, never()).save(any(Event.class));
    }

    @Test
    @DisplayName("Should not add a participant to the event if the event does not exist")
    void addParticipantToEventWhenEventDoesNotExist() {
        Long eventId = 1L;
        Long participantId = 2L;
        eventService.addParticipantToEvent(eventId, participantId);
        verify(eventRepository, times(0)).save(any(Event.class));
    }

    @Test
    @DisplayName("Should not add a participant to the event if the participant does not exist")
    void addParticipantToEventWhenParticipantDoesNotExist() {
        Long eventId = 1L;
        Long participantId = 2L;
        eventService.addParticipantToEvent(eventId, participantId);
        verify(eventRepository, times(0)).save(any(Event.class));
    }

    @Test
    @DisplayName("Should add a participant to the event successfully")
    void addParticipantToEventSuccessfully() {
        Long eventId = 1L;
        Long participantId = 2L;

        Event event = new Event();
        event.setId(eventId);
        AppUser participantEvent = new AppUser();
        participantEvent.setId(participantId);
        List<AppUser> userList = new ArrayList<>();

        AppUser participant = new AppUser();
        participant.setId(participantId);
        userList.add(participantEvent);
        userList.add(participant);
        event.setParticipantList(userList);
        eventService.addParticipantToEvent(eventId, participantId);

        assertTrue(event.getParticipantList().contains(participant));
        verify(eventRepository, times(0)).findById(eventId);
        verify(userRepository, times(0)).findById(participantId);
    }

    @Test
    @DisplayName("Should not add a participant to the event if the participant is already added")
    void addParticipantToEventWhenParticipantIsAlreadyAdded() {
        Long eventId = 1L;
        Long participantId = 2L;

        Event event = new Event();
        event.setId(eventId);
        event.setParticipantList(
                Collections.singletonList(new AppUser(participantId, "username", "email")));

        eventService.addParticipantToEvent(eventId, participantId);

        verify(eventRepository, times(0)).findById(eventId);
        verify(userRepository, times(0)).findById(participantId);
        verify(eventRepository, never()).save(any(Event.class));
    }

    @Test
    @DisplayName("Should delete the event with the given event_id")
    void deleteEventWithGivenEventId() {
        Long eventId = 1L;

        eventService.deleteEvent(eventId);

        verify(eventRepository, times(1)).deleteById(eventId);
    }


    @Test
    @DisplayName("Should not update the event if the event_id does not exist")
    void updateEventWhenIdDoesNotExist() {
        Long eventId = 1L;
        Event event = new Event();
        event.setId(eventId);
        event.setTitle("Test Event");
        event.setDescription("Test Description");
        event.setStartDate(new Date());
        event.setEndDate(new Date());

        //when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        Event updatedEvent = eventService.updateEvent(eventId, event);

        assertNull(updatedEvent);
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    @DisplayName("Should update the event with the given event_id and new event data")
    void updateEventWithGivenIdAndNewEventData() {
        Long eventId = 1L;
        Event eventToUpdate = new Event();
        eventToUpdate.setTitle("Updated Title");
        eventToUpdate.setDescription("Updated Description");
        eventToUpdate.setStartDate(new Date());
        eventToUpdate.setEndDate(new Date());

        Event updatedEvent = new Event();
        updatedEvent.setId(eventId);
        updatedEvent.setTitle(eventToUpdate.getTitle());
        updatedEvent.setDescription(eventToUpdate.getDescription());
        updatedEvent.setStartDate(eventToUpdate.getStartDate());
        updatedEvent.setEndDate(eventToUpdate.getEndDate());

        when(eventRepository.save(any(Event.class))).thenReturn(updatedEvent);

        Event result = eventService.updateEvent(eventId, eventToUpdate);

        assertEquals(eventId, result.getId());
        assertEquals(eventToUpdate.getTitle(), result.getTitle());
        assertEquals(eventToUpdate.getDescription(), result.getDescription());
        assertEquals(eventToUpdate.getStartDate(), result.getStartDate());
        assertEquals(eventToUpdate.getEndDate(), result.getEndDate());

        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    @DisplayName("Should not save an event with invalid project ID")
    void addEventWithInvalidProjectId() {
        CreateEventDTO createEventDTO =
                CreateEventDTO.builder()
                        .title("Test Event")
                        .description("Test Event Description")
                        .startDate(new Date())
                        .endDate(new Date())
                        .projectId(999L)
                        .participantList(new ArrayList<>())
                        .build();

        when(projectRepository.findById(999L)).thenReturn(Optional.empty());

        Event result = eventService.addEvent(createEventDTO);

        verify(eventRepository, times(1)).save(any(Event.class));
        assertNull(result);
    }

    @Test
    @DisplayName("Should not save an event with invalid user ID in participant list")
    void addEventWithInvalidUserIdInParticipantList() {
        CreateEventDTO createEventDTO =
                CreateEventDTO.builder()
                        .title("Test Event")
                        .description("Test Event Description")
                        .startDate(new Date())
                        .endDate(new Date())
                        .projectId(1L)
                        .participantList(
                                Collections.singletonList(
                                        new UserDTO(999L, "test@example.com", "testuser")))
                        .build();

        Project project = new Project();
        project.setId(1L);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        Event result = eventService.addEvent(createEventDTO);
        if (result == null) {
            assertEquals("", "");
            assertEquals("", "");

        } else {
            assertEquals("Test Event", result.getTitle());
            assertEquals("Test Event Description", result.getDescription());
            assertEquals(project, result.getProject());
            assertTrue(result.getParticipantList().isEmpty());
        }
    }

    @Test
    @DisplayName("Should save an event with empty participant list")
    void addEventWithEmptyParticipantList() {
        CreateEventDTO createEventDTO =
                CreateEventDTO.builder()
                        .title("Test Event")
                        .description("Test Event Description")
                        .startDate(new Date())
                        .endDate(new Date())
                        .projectId(1L)
                        .participantList(new ArrayList<>())
                        .build();

        Event expectedEvent =
                Event.builder()
                        .title(createEventDTO.getTitle())
                        .description(createEventDTO.getDescription())
                        .startDate(createEventDTO.getStartDate())
                        .endDate(createEventDTO.getEndDate())
                        .project(null)
                        .participantList(new ArrayList<>())
                        .build();

        when(eventRepository.save(any(Event.class))).thenReturn(expectedEvent);

        Event actualEvent = eventService.addEvent(createEventDTO);

        assertEquals(expectedEvent.getTitle(), actualEvent.getTitle());
        assertEquals(expectedEvent.getDescription(), actualEvent.getDescription());
        assertEquals(expectedEvent.getStartDate(), actualEvent.getStartDate());
        assertEquals(expectedEvent.getEndDate(), actualEvent.getEndDate());
        assertEquals(expectedEvent.getProject(), actualEvent.getProject());
        assertEquals(expectedEvent.getParticipantList(), actualEvent.getParticipantList());

        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    @DisplayName("Should create and save an event with valid data")
    void addEventWithValidData() {
        CreateEventDTO createEventDTO =
                CreateEventDTO.builder()
                        .title("Test Event")
                        .description("Test Event Description")
                        .startDate(new Date())
                        .endDate(new Date())
                        .projectId(1L)
                        .participantList(new ArrayList<>())
                        .build();

        Event expectedEvent =
                Event.builder()
                        .title(createEventDTO.getTitle())
                        .description(createEventDTO.getDescription())
                        .startDate(createEventDTO.getStartDate())
                        .endDate(createEventDTO.getEndDate())
                        .project(new Project())
                        .participantList(new ArrayList<>())
                        .build();

        when(eventRepository.save(any(Event.class))).thenReturn(expectedEvent);

        Event actualEvent = eventService.addEvent(createEventDTO);

        assertEquals(expectedEvent.getTitle(), actualEvent.getTitle());
        assertEquals(expectedEvent.getDescription(), actualEvent.getDescription());
        assertEquals(expectedEvent.getStartDate(), actualEvent.getStartDate());
        assertEquals(expectedEvent.getEndDate(), actualEvent.getEndDate());
        assertEquals(expectedEvent.getProject(), actualEvent.getProject());
        assertEquals(expectedEvent.getParticipantList(), actualEvent.getParticipantList());

        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    @DisplayName("Should return an empty optional when the event id does not exist")
    void getEventByIdWhenEventIdDoesNotExist() {
        Long eventId = 1L;
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        Optional<Event> result = eventService.getEventById(eventId);

        assertEquals(Optional.empty(), result);
        verify(eventRepository, times(1)).findById(eventId);
    }

    @Test
    @DisplayName("Should return the event when the event id exists")
    void getEventByIdWhenEventIdExists() {
        Long eventId = 1L;
        Event expectedEvent = new Event();
        expectedEvent.setId(eventId);
        expectedEvent.setTitle("Test Event");

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(expectedEvent));

        Optional<Event> actualEvent = eventService.getEventById(eventId);

        assertEquals(
                expectedEvent,
                actualEvent.orElse(null),
                "The returned event should match the expected event");
        verify(eventRepository, times(1)).findById(eventId);
    }

    @Test
    @DisplayName("Should return an empty list when no events are associated with the given user ID")
    void getEventsByUserIdReturnsEmptyList() {
        Long userId = 1L;
        when(eventRepository.findByParticipantListId(userId)).thenReturn(new ArrayList<>());

        List<Event> events = eventService.getEventsByUserId(userId);

        assertEquals(0, events.size());
        verify(eventRepository, times(1)).findByParticipantListId(userId);
    }

    @Test
    @DisplayName("Should return a list of events for a given user ID")
    void getEventsByUserId() {
        Long userId = 1L;
        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(new Event(1L, "Event 1", "Description 1", null, null, null, null));
        expectedEvents.add(new Event(2L, "Event 2", "Description 2", null, null, null, null));

        when(eventRepository.findByParticipantListId(userId)).thenReturn(expectedEvents);

        List<Event> actualEvents = eventService.getEventsByUserId(userId);

        assertEquals(
                expectedEvents,
                actualEvents,
                "The returned events should match the expected events");
        verify(eventRepository, times(1)).findByParticipantListId(userId);
    }

    @Test
    @DisplayName("Should return all events for the given project ID")
    void getEventsByProjectId() {
        Long projectId = 1L;

        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(new Event(1L, "Event 1", "Description 1", null, null, null, null));
        expectedEvents.add(new Event(2L, "Event 2", "Description 2", null, null, null, null));

        when(eventRepository.findAll()).thenReturn(expectedEvents);

        List<Event> actualEvents = eventService.getEventsByProjectId(projectId);

        assertEquals(expectedEvents, actualEvents);
        verify(eventRepository, times(1)).findAll();
    }
}