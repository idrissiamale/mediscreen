package com.clientui.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A model class which gathers the patient's data.
 * This DTO is annotated using Hibernate-Validation annotations. We use it to add constraints on fields, in order to validate user's inputs.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @NotNull
    private Integer id;

    @NotBlank(message = "Please enter the first name")
    @Size(min = 3, message = "The first name should have at least 3 characters")
    @Size(max = 45, message = "The first name should have maximum 45 characters")
    private String family;

    @NotBlank(message = "Please enter the last name")
    @Size(min = 3, message = "The last name should have at least 3 characters")
    @Size(max = 45, message = "The last name should have maximum 45 characters")
    private String given;

    @NotBlank(message = "Please enter the date of birth")
    private String dob;

    @NotBlank(message = "Please enter the patient gender")
    @Size(min = 1, message = "The gender should have only 1 character : M or F")
    @Size(max = 1, message = "The gender should have only 1 character : M or F")
    private String sex;

    private String address;

    private String phone;
}
