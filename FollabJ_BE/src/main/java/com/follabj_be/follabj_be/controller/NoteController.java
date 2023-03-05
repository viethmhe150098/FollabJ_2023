package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.dto.NoteDTO;
import com.follabj_be.follabj_be.dto.TaskDTO;
import com.follabj_be.follabj_be.entity.Note;
import com.follabj_be.follabj_be.entity.Task;
import com.follabj_be.follabj_be.service.impl.NoteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class NoteController {
    @Autowired
    NoteService noteService;

    private ModelMapper modelMapper;

    @GetMapping("/notes/{user_id}")
    public List<NoteDTO> getNotesByUserId(@PathVariable Long user_id) {
        List<Note> noteList = noteService.getNotesByCreatorId(user_id);
        List<NoteDTO> noteDTOList = new ArrayList<>();
        for (Note note: noteList) {
            NoteDTO noteDTO = modelMapper.map(note, NoteDTO.class);
            noteDTOList.add(noteDTO);
        }

        return noteDTOList;
    }

    @PostMapping("/notes/{user_id}")
    public NoteDTO addNote(@RequestBody Note note, @PathVariable Long user_id) {
        Note addedNote = noteService.addNote(note);
        NoteDTO noteDTO = modelMapper.map(note, NoteDTO.class);
        return noteDTO;
    }

    @PutMapping("/notes/{user_id}")
    public NoteDTO updateNote(@RequestParam Long note_id, @RequestBody Note note) {
        Note updatedNote = noteService.updateNote(note_id, note);
        NoteDTO noteDTO = modelMapper.map(note, NoteDTO.class);
        return noteDTO;
    }

    @DeleteMapping("/notes/{user_id}")
    public void deleteNote(@RequestParam Long note_id) {
        noteService.deleteNote(note_id);
    }
}
