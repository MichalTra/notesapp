package com.notesapp.notesapp.repository;

import com.notesapp.notesapp.model.Holiday;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface HolidayRepository extends CrudRepository<Holiday, Integer> {

    List<Holiday> findByLocalization(String localization);

}
