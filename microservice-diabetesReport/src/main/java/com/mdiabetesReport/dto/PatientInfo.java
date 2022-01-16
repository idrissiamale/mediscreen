package com.mdiabetesReport.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientInfo {
    private Integer id;
    private String familyName;
    private String given;
    private int age;
    private String sex;
    private String diabetesLevel;
}
