package com.follabj_be.follabj_be.repository;

import com.follabj_be.follabj_be.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByCreatorId(Long creator_id);

}
