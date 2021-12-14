package com.mpatient.dto;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotNull
    @Size(min = 3, max = 45)
    private String family;

    @NotNull
    @Size(min = 3, max = 45)
    private String given;

    @NotNull
    private String dob;

    @NotNull
    @Column(length = 1)
    private String sex;

    private String address;
    
    private String phone;
}
