package com.mnote.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

/**
 * A model class which represents the patient's note(s). The entity is mapped to our NoSQL database.
 * It is also annotated using Hibernate-Validation annotations in order to add constraints on fields and to validate user's inputs.
 */
@Getter
@Setter
@AllArgsConstructor
@Document(collection = "PatientHistory")
public class Note {
    @Id
    @Field("_id")
    private String id;

    @NotNull(message = "The patient's id is mandatory")
    private Integer patId;

    @NotNull(message = "Notes are mandatory")
    private String e;
}
