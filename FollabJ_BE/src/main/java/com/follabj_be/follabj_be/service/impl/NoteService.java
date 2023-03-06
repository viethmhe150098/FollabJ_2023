package com.follabj_be.follabj_be.service.impl;

import com.follabj_be.follabj_be.entity.Note;
import com.follabj_be.follabj_be.repository.NoteRepository;
import com.follabj_be.follabj_be.service.NoteInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Access;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService implements NoteInterface {

    @Autowired
    NoteRepository noteRepository;

    @Override
    public List<Note> getNotesByCreatorId(Long creator_id) {
        return noteRepository.findByCreatorId(creator_id);
    }

    @Override
    public Optional<Note> getNoteById(Long note_id) {
        return noteRepository.findById(note_id);
    }

    @Override
    public Note addNote(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public Note updateNote(Long id, Note note) {
        note.setId(id);
        return noteRepository.save(note);
    }

    @Override
    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }
}
