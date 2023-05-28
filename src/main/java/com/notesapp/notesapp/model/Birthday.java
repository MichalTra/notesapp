package com.notesapp.notesapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Data
@Entity
public class Birthday {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;
    @NotNull(message = "User (owner) ID must be defined")
    private int userId;
    @NotNull(message = "The birthday date cannot be undefined")
    private Date date;
    @NotNull(message = "Description must not be empty")
    @NotBlank(message = "Description must not be empty")
    @Size(min=1, max=50, message = "Description must be at least one character long, at most 50 characters long")
    private String description;

}