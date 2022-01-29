package com.mdiabetesReport.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * A DTO class which gathers the following patient's data : id, last name, first name, age, gender and diabetes level.
 */
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
