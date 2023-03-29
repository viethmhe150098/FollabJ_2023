package com.follabj_be.follabj_be.service;

import com.follabj_be.follabj_be.entity.Note;

import java.util.List;
import java.util.Optional;

public interface NoteInterface {

    List<Note> getNotesByCreatorId(Long creator_id);

    Optional<Note> getNoteById(Long note_id);

    Note addNote(Note note);

    Note updateNote (Note note);

    void deleteNote(Long id);

}
