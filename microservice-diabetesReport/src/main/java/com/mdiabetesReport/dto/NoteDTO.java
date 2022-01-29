package com.mdiabetesReport.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * A DTO class which gathers patient's notes/recommendations written by his/her practitioner.
 */
@Getter
@AllArgsConstructor
public class NoteDTO {
    private String id;

    private Integer patId;

    private String note;
}
