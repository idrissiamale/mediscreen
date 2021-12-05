package com.mpatient.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * A DTO class which gathers patient's information. It provides his/her family name, given name, birthdate, gender, address and phone.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PatientRegistrationDto {
    private String family;
    private String given;
    private String dob;
    private String sex;
    private String address;
    private String phone;
}
