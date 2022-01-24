package com.clientui.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * A model class which represents the patient's note(s).
 * This class is annotated using Hibernate-Validation annotations. We use it to add constraints on fields, in order to validate user's inputs.
 */
@Getter
@Setter
@AllArgsConstructor
public class Note {
    @NotNull
    private String id;

    @NotNull(message = "Please enter the id of the patient")
    private Integer patId;

    @NotNull(message = "Please add your notes or recommendations")
    private String e;
}
