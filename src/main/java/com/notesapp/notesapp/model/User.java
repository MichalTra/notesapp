package com.notesapp.notesapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Table(name = "users")
@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long userId;

    @NotNull(message = "The username must be set")
    @Size(min=3, max=20, message = "Username must be between 3 and 20 characters long")
    private String username;

    @NotNull(message = "Password must be set")
    private String password;

    // language will be related to localization in future
    @NotNull(message = "The localization info must be set")
    @NotBlank(message = "The localization info must be set")
    private String language = "CS";

    @Transient
    private boolean createNewUser;

}
