package com.mdiabetesReport.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PatientInfo {
    private Integer id;
    private String familyName;
    private String given;
    private int age;
    private String sex;
    private String diabetesLevel;
}
