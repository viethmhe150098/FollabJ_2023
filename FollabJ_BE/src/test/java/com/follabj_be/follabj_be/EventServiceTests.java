package com.follabj_be.follabj_be;
import com.follabj_be.follabj_be.dto.CreateEventDTO;
import com.follabj_be.follabj_be.entity.Event;
import com.follabj_be.follabj_be.repository.EventRepository;
import com.follabj_be.follabj_be.service.EventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Date;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventServiceTests {
    @Mock
    EventRepository eventRepository;

    @InjectMocks
    EventService eventService;

    @Test
    public void Event_TestUpdateEvent() {
        Event event = new Event();
        event.setId(1L);
        when(eventRepository.save(event)).thenReturn(event);
        Event updateEvent = eventService.updateEvent(1L, event);
        assertThat(event).isEqualTo(updateEvent);
        verify(eventRepository).save(event);
    }

    @Test
    public void Event_TestDeleteEvent() {
        Long eventID = 1L;
        eventService.deleteEvent(eventID);
        verify(eventRepository).deleteById(eventID);
    }

    @Test
    public void Event_TestGetEventById() {
        Long eventID = 1L;
        Optional<Event> event = Optional.of(Event.builder().id(eventID).build());
        Mockito.when(eventRepository.findById(eventID)).thenReturn(event);
        Optional<Event> foundEvent = eventService.getEventById(eventID);
        assertThat(event).isEqualTo(foundEvent);
        verify(eventRepository).findById(eventID);
    }

    @Test
    public void Event_TestCreateEvent() {
        Event event = new Event();
        when(eventRepository.save(event)).thenReturn(event);
        Event savedEvent = eventService.addEvent(CreateEventDTO.builder().title("title").description("des").startDate(new Date(2020,1,1)).endDate(new Date(2023,1,1)).projectId(1L).build());
        assertThat(event).isEqualTo(savedEvent);
        verify(eventRepository).save(event);
    }
}
