package com.follabj_be.follabj_be;

import com.follabj_be.follabj_be.entity.Note;
import com.follabj_be.follabj_be.entity.Task;
import com.follabj_be.follabj_be.repository.NoteRepository;
import com.follabj_be.follabj_be.repository.ProjectRepository;
import com.follabj_be.follabj_be.service.impl.NoteService;
import com.follabj_be.follabj_be.service.impl.ProjectService;
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
public class NoteServiceTests {
    @Mock
    NoteRepository noteRepository;

    @InjectMocks
    NoteService noteService;

    @Test
    public void Note_TestUpdateNote() {
        Note note = new Note();
        note.setId(1L);
        when(noteRepository.save(note)).thenReturn(note);
        Note updateNote = noteService.updateNote(1L, note);
        assertThat(note).isEqualTo(updateNote);
        verify(noteRepository).save(note);
    }

    @Test
    public void Note_TestDeleteNote() {
        Long noteID = 1L;
        noteRepository.deleteById(noteID);
        verify(noteRepository).deleteById(noteID);
    }

    @Test
    public void Note_TestGetNoteById() {
        Long noteID = 1L;
        Optional<Note> note = Optional.of(Note.builder().id(noteID).build());
        Mockito.when(noteRepository.findById(noteID)).thenReturn(note);
        Optional<Note> foundNote = noteService.getNoteById(noteID);
        assertThat(note).isEqualTo(foundNote);
        verify(noteRepository).findById(noteID);
    }

    @Test
    public void Note_TestCreateNote() {
        Note note = new Note();
        when(noteRepository.save(note)).thenReturn(note);
        Note savedNote = noteRepository.save(note);
        assertThat(note).isEqualTo(savedNote);
        verify(noteRepository).save(note);
    }
}
