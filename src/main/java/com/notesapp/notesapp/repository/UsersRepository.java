package com.notesapp.notesapp.repository;

import com.notesapp.notesapp.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<User, Integer> {

    User getByUsername(String username);

}
