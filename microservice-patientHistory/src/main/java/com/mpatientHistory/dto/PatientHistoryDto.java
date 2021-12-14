package com.mpatientHistory.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PatientHistoryDto {
    @NotNull
    private Integer id;

    @NotNull
    private String e;
}
