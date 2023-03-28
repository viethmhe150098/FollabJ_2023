package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.dto.NoteDTO;
import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.Note;
import com.follabj_be.follabj_be.repository.NoteRepository;
import com.follabj_be.follabj_be.service.impl.NoteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("NoteController")
class NoteControllerTest {
    @Mock
    NoteService noteService;
    @Mock
    NoteRepository noteRepository;

    @InjectMocks
    NoteController noteController;

    ModelMapper modelMapper = new ModelMapper();

    @Test
    @DisplayName("should return an empty list when given an invalid user_id")
    void getNotesByUserIdWithInvalidUserId() {
        Long user_id = 1L;
        List<Note> noteList = new ArrayList<>();
        when(noteService.getNotesByCreatorId(user_id)).thenReturn(noteList);

        List<NoteDTO> noteDTOList = noteController.getNotesByUserId(user_id);

        assertThat(noteDTOList).isEmpty();
    }

    @Test
    @DisplayName("should return a list of NoteDTOs when given a valid user_id")
    void getNotesByUserIdWithValidUserId() {
        AppUser user = new AppUser();
        user.setId(1L);
        Note note = new Note();
        note.setId(1L);
        note.setTitle("title");
        note.setContent("content");
        note.setCreatedDate(new Date());
        note.setUpdatedDate(new Date());
        note.setCreator(user);
        List<Note> noteList = new ArrayList<>();
        noteList.add(note);
        when(noteService.getNotesByCreatorId(1L)).thenReturn(noteList);

        List<NoteDTO> noteDTOList = noteController.getNotesByUserId(1L);

        assertThat(noteDTOList).isNotNull();
        assertThat(noteDTOList.size()).isEqualTo(1);
        assertThat(noteDTOList.get(0).getId()).isEqualTo(1L);
        assertThat(noteDTOList.get(0).getTitle()).isEqualTo("title");
        assertThat(noteDTOList.get(0).getContent()).isEqualTo("content");
        assertThat(noteDTOList.get(0).getCreatedDate()).isNotNull();
        assertThat(noteDTOList.get(0).getUpdatedDate()).isNotNull();
        assertThat(noteDTOList.get(0).getCreator()).isNotNull();
    }
}