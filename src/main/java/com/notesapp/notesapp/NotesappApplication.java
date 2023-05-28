package com.notesapp.notesapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.notesapp.notesapp.repository")
@EntityScan("com.notesapp.notesapp.model")
public class NotesappApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotesappApplication.class, args);
	}

}
