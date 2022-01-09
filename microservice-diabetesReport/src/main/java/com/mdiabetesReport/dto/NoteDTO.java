package com.mdiabetesReport.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class NoteDTO {
    private String id;

    @NotNull
    private Integer patId;

    @NotNull
    private String note;

    public NoteDTO(@NotNull Integer patId, @NotNull String note) {
        this.patId = patId;
        this.note = note;
    }
}
