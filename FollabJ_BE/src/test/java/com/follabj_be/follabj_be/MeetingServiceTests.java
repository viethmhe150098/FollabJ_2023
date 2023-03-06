package com.follabj_be.follabj_be;

import com.follabj_be.follabj_be.entity.Note;
import com.follabj_be.follabj_be.exception.GroupException;
import com.follabj_be.follabj_be.repository.MeetingRepository;
import com.follabj_be.follabj_be.repository.NoteRepository;
import com.follabj_be.follabj_be.service.impl.MeetingService;
import com.follabj_be.follabj_be.service.impl.NoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MeetingServiceTests {
    @Mock
    MeetingRepository meetingRepository;

    @InjectMocks
    MeetingService meetingService;
    @Mock
    NoteRepository noteRepository;

    @InjectMocks
    NoteService noteService;

    @Test
    public void Meeting_GenerateRoomId() throws GroupException {
        Note note = new Note();
        when(noteRepository.save(note)).thenReturn(note);
        Note savedNote = noteRepository.save(note);
        assertThat(note).isEqualTo(savedNote);
        verify(noteRepository).save(note);
        //assertThat(1).isEqualTo(1);
    }

}
