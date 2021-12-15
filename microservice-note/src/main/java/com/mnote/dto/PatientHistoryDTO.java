package com.mnote.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class PatientHistoryDTO {
    private Integer patId;
    @NotNull
    private String e;
}
