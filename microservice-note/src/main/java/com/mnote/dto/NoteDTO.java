package com.mnote.dto;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

/**
 * A DTO class which gathers patient's notes/recommendations written by his/her practitioner.
 * This DTO is annotated using Hibernate-Validation annotations. We use it to add constraints on fields, in order to validate user's inputs.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteDTO {
    @Id
    private String id;

    @NotNull
    private Integer patId;

    @NotNull
    private String note;
}
