package com.follabj_be.follabj_be.controller;

import com.follabj_be.follabj_be.dto.NoteDTO;
import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.Note;
import com.follabj_be.follabj_be.service.impl.NoteService;
import com.follabj_be.follabj_be.service.impl.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class NoteController {
    @Autowired
    NoteService noteService;

    @Autowired
    UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/notes")
    public List<NoteDTO> getNotesByUserId(Authentication authentication ) {
        Long user_id = getUserId(authentication);
        List<Note> noteList = noteService.getNotesByCreatorId(user_id);
        List<NoteDTO> noteDTOList = new ArrayList<>();
        for (Note note : noteList) {
            NoteDTO noteDTO = modelMapper.map(note, NoteDTO.class);
            noteDTOList.add(noteDTO);
        }

        return noteDTOList;
    }

    @GetMapping("/notes/{note_id}")
    public NoteDTO getNoteByNoteId(Authentication authentication , @PathVariable Long note_id) {
        Long user_id = getUserId(authentication);
        checkIfNoteBelongToUser(note_id, user_id);

        Optional<Note> optinalNote = noteService.getNoteById(note_id);

        if (optinalNote.isPresent()) {
            Note note = optinalNote.get();
            NoteDTO noteDTO = modelMapper.map(note, NoteDTO.class);
            return noteDTO;
        } else {
            throw new RuntimeException("Note id not found");
        }
    }

    @PostMapping("/notes")
    public NoteDTO addNote(Authentication authentication ,@Valid @RequestBody Note note) {
        Long user_id = getUserId(authentication);

        AppUser user = new AppUser();
        user.setId(user_id);
        note.setCreator(user);
        Note addedNote = noteService.addNote(note);
        NoteDTO noteDTO = modelMapper.map(note, NoteDTO.class);
        return noteDTO;
    }

    @PutMapping("/notes/{note_id}")
    public void updateNote(HttpServletResponse response, Authentication authentication ,@Valid @RequestBody Note note, @PathVariable Long note_id) throws IOException {
        Long user_id = getUserId(authentication);
        checkIfNoteBelongToUser(note_id, user_id);
        note.setId(note_id);
        AppUser user = new AppUser();
        user.setId(user_id);
        note.setCreator(user);
        Note updatedNote = noteService.updateNote(note);
        //response.sendRedirect("/notes/"+note_id);
    }

    @DeleteMapping("/notes/{note_id}")
    public void deleteNote(Authentication authentication , @PathVariable Long note_id) {
        Long user_id = getUserId(authentication);
        checkIfNoteBelongToUser(note_id, user_id);
        noteService.deleteNote(note_id);
    }

    public Long getUserId(Authentication authentication) {
        return userService.getUserByEmail(authentication.getPrincipal().toString()).getId();
    }

    public void checkIfNoteBelongToUser(Long note_id, Long user_id) {
        if(!noteService.checkIfNoteBelongToUser(note_id, user_id)) {
            throw new RuntimeException("Note id not existed");
        };
    }


}
