package com.mpatient.dto;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A DTO class which gathers patient's information. It provides his/her family name, given name, birthdate, gender, address and phone.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientRegistrationDTO {
    @NotNull(message = "The first name is mandatory")
    @Size(min = 3, max = 45, message = "The first name should have at least 3 characters")
    private String family;

    @NotNull(message = "The last name is mandatory")
    @Size(min = 3, max = 45, message = "The last name should have at least 3 characters")
    private String given;

    @NotNull(message = "The date of birth is mandatory")
    private String dob;

    @NotNull(message = "The gender is mandatory")
    @Size(min = 1, message = "The gender should have only 1 character : M or F")
    @Size(max = 1, message = "The gender should have only 1 character : M or F")
    private String sex;

    private String address;

    private String phone;
}
