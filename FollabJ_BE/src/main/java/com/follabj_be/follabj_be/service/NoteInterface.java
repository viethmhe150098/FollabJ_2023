package com.follabj_be.follabj_be.service;

import com.follabj_be.follabj_be.entity.Note;

import java.util.List;

public interface NoteInterface {

     List<Note> getNotesByCreatorId(Long creator_id);

     Note addNote(Note note);

     Note updateNote (Long id, Note note);

     void deleteNote (Long id);

}
