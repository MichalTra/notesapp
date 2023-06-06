package com.notesapp.notesapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private long userId;

    @NotNull(message = "Description must not be empty")
    @NotBlank(message = "Description must not be empty")
    @Size(min=1, max=200, message = "Description must be at least one character long, at most 200 characters long")
    private String description;
    private Date deadline;

    @LastModifiedDate
    @Column(insertable = false)
    private Date modifiedAt;

    private boolean done;

    private String labels;
}
