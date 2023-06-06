package com.notesapp.notesapp.repository;

import com.notesapp.notesapp.model.Birthday;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BirthdayRepository extends CrudRepository<Birthday, Integer> {

    List<Birthday> findByUserId(long userId);

}
