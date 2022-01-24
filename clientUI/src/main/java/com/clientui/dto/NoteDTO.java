package com.clientui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * A DTO class which gathers patient's notes/recommendations written by his/her practitioner.
 * This DTO is annotated using Hibernate-Validation annotations. We use it to add constraints on fields, in order to validate user's inputs.
 */
@Getter
@Setter
@AllArgsConstructor
public class NoteDTO {
    private String id;

    @NotNull(message = "Please enter the id of the patient")
    private Integer patId;

    @NotBlank(message = "Please add your notes or recommendations")
    private String note;
}
