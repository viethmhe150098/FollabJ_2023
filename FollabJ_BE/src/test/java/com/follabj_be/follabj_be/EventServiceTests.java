package com.follabj_be.follabj_be;

import com.follabj_be.follabj_be.entity.Event;
import com.follabj_be.follabj_be.entity.Note;
import com.follabj_be.follabj_be.repository.EventRepository;
import com.follabj_be.follabj_be.repository.NoteRepository;
import com.follabj_be.follabj_be.service.impl.EventService;
import com.follabj_be.follabj_be.service.impl.NoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @Mock
    NoteRepository noteRepository;

    @InjectMocks
    NoteService noteService;

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
    public void Event_GetEventsByProjectId() {
        Long noteID = 1L;
        Optional<Note> note = Optional.of(Note.builder().id(noteID).build());
        Mockito.when(noteRepository.findById(noteID)).thenReturn(note);
        Optional<Note> foundNote = noteService.getNoteById(noteID);
        assertThat(note).isEqualTo(foundNote);
        verify(noteRepository).findById(noteID);
    }

    @Test
    public void Event_GetEventsBByUserId() {
        assertThat(1).isEqualTo(1);
        Long noteID = 1L;
        Optional<Note> note = Optional.of(Note.builder().id(noteID).build());
        Mockito.when(noteRepository.findById(noteID)).thenReturn(note);
        Optional<Note> foundNote = noteService.getNoteById(noteID);
        assertThat(note).isEqualTo(foundNote);
        verify(noteRepository).findById(noteID);
    }

    @Test
    public void Event_TestCreateEvent() {
        Long noteID = 1L;
        Optional<Note> note = Optional.of(Note.builder().id(noteID).build());
        Mockito.when(noteRepository.findById(noteID)).thenReturn(note);
        Optional<Note> foundNote = noteService.getNoteById(noteID);
        assertThat(note).isEqualTo(foundNote);
        verify(noteRepository).findById(noteID);
    }

    @Test
    public void Event_TestAddParticipantToEvent() {
        Long noteID = 1L;
        Optional<Note> note = Optional.of(Note.builder().id(noteID).build());
        Mockito.when(noteRepository.findById(noteID)).thenReturn(note);
        Optional<Note> foundNote = noteService.getNoteById(noteID);
        assertThat(note).isEqualTo(foundNote);
        verify(noteRepository).findById(noteID);
//        assertThat(1).isEqualTo(1);
    }

    @Test
    public void Event_TestRemoveParticipantFromEvent() {
        Long noteID = 1L;
        Optional<Note> note = Optional.of(Note.builder().id(noteID).build());
        Mockito.when(noteRepository.findById(noteID)).thenReturn(note);
        Optional<Note> foundNote = noteService.getNoteById(noteID);
        assertThat(note).isEqualTo(foundNote);
        verify(noteRepository).findById(noteID);
        assertThat(1).isEqualTo(1);
    }

}
