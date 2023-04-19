package com.follabj_be.follabj_be.service.impl;

import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.Note;
import com.follabj_be.follabj_be.repository.NoteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    NoteRepository noteRepository;

    @InjectMocks
    NoteService noteService;

    @Test
    @DisplayName("Should delete the note with the given id")
    void deleteNoteWithGivenId() {
        Long noteId = 1L;

        noteService.deleteNote(noteId);

        verify(noteRepository, times(1)).deleteById(noteId);
    }

    @Test
    @DisplayName("Should throw an exception when the note with the given id does not exist")
    void deleteNoteWhenIdDoesNotExistThenThrowException() {
        Long nonExistentId = 1L;
        doThrow(new NoSuchElementException()).when(noteRepository).deleteById(nonExistentId);

        try {
            noteService.deleteNote(nonExistentId);
        } catch (Exception e) {
            assertTrue(e instanceof NoSuchElementException);
            verify(noteRepository, times(1)).deleteById(nonExistentId);
        }
    }

    @Test
    @DisplayName("Should update the note with the given note object")
    void updateNoteWithGivenNoteObject() {
        Note note =
                Note.builder()
                        .id(1L)
                        .title("Test Title")
                        .content("Test Content")
                        .creator(new AppUser())
                        .build();

        Note updatedNote =
                Note.builder()
                        .id(1L)
                        .title("Updated Title")
                        .content("Updated Content")
                        .creator(new AppUser())
                        .build();

        when(noteRepository.save(note)).thenReturn(updatedNote);

        Note result = noteService.updateNote(note);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(updatedNote.getId());
        assertThat(result.getTitle()).isEqualTo(updatedNote.getTitle());
        assertThat(result.getContent()).isEqualTo(updatedNote.getContent());
        assertThat(result.getCreator()).isEqualTo(updatedNote.getCreator());

        verify(noteRepository, times(1)).save(note);
    }

    @Test
    @DisplayName("Should save the note and return the saved note")
    void addNoteSuccessfully() {
        Note note =
                Note.builder()
                        .title("Test Title")
                        .content("Test Content")
                        .creator(
                                AppUser.builder()
                                        .id(1L)
                                        .username("testuser")
                                        .email("test@example.com")
                                        .build())
                        .build();

        when(noteRepository.save(note)).thenReturn(note);

        Note savedNote = noteService.addNote(note);

        assertThat(savedNote).isNotNull();
        assertThat(savedNote.getTitle()).isEqualTo("Test Title");
        assertThat(savedNote.getContent()).isEqualTo("Test Content");
        assertThat(savedNote.getCreator().getId()).isEqualTo(1L);
        assertThat(savedNote.getCreator().getUsername()).isEqualTo("testuser");
        assertThat(savedNote.getCreator().getEmail()).isEqualTo("test@example.com");

        verify(noteRepository, times(1)).save(note);
    }

    @Test
    @DisplayName("Should return an empty optional when the note id is not found")
    void getNoteByIdWhenNoteIdIsNotFound() {
        Long noteId = 1L;
        when(noteRepository.findById(noteId)).thenReturn(Optional.empty());

        Optional<Note> result = noteService.getNoteById(noteId);

        assertTrue(result.isEmpty());
        verify(noteRepository, times(1)).findById(noteId);
    }

    @Test
    @DisplayName("Should return the note when the note id is valid")
    void getNoteByIdWhenNoteIdIsValid() {
        Long noteId = 1L;
        Note expectedNote =
                new Note(1L, "Test Title", "Test Content", new Date(), new Date(), new AppUser());
        when(noteRepository.findById(noteId)).thenReturn(Optional.of(expectedNote));

        Optional<Note> actualNote = noteService.getNoteById(noteId);

        assertTrue(actualNote.isPresent());
        assertThat(actualNote.get()).isEqualTo(expectedNote);
        verify(noteRepository, times(1)).findById(noteId);
    }

    @Test
    @DisplayName("Should return an empty list when there are no notes for the given creator_id")
    void getNotesByCreatorIdWhenNoNotesForGivenCreatorId() {
        Long creatorId = 1L;
        when(noteRepository.findByCreatorId(creatorId)).thenReturn(Collections.emptyList());

        List<Note> result = noteService.getNotesByCreatorId(creatorId);

        assertTrue(result.isEmpty());
        verify(noteRepository, times(1)).findByCreatorId(creatorId);
    }

    @Test
    @DisplayName("Should return a list of notes when the creator_id is valid")
    void getNotesByCreatorIdWhenCreatorIdIsValid() {
        Long creatorId = 1L;
        AppUser creator = new AppUser(creatorId, "username", "email");
        List<Note> expectedNotes = new ArrayList<>();
        expectedNotes.add(new Note(1L, "Title 1", "Content 1", new Date(), new Date(), creator));
        expectedNotes.add(new Note(2L, "Title 2", "Content 2", new Date(), new Date(), creator));

        when(noteRepository.findByCreatorId(creatorId)).thenReturn(expectedNotes);

        List<Note> actualNotes = noteService.getNotesByCreatorId(creatorId);

        assertThat(actualNotes).isEqualTo(expectedNotes);
        verify(noteRepository, times(1)).findByCreatorId(creatorId);
    }
}