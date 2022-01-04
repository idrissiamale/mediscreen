package com.mdiabetesReport.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class NoteDTO {
    private String id;

    @NotNull
    private Integer patId;

    @NotNull
    private String note;
}
