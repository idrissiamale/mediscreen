package com.mdiabetesReport.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class NoteDTO {
    private String id;

    @NotNull
    private Integer patId;

    @NotNull
    private String note;
}
