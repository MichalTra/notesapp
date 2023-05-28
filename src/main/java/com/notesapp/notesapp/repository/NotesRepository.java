package com.notesapp.notesapp.repository;

import com.notesapp.notesapp.model.Note;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotesRepository extends CrudRepository<Note, Integer> {

    List<Note> findByUserId(int userId);
    Note findById(int id);

}
