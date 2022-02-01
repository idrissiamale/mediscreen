package com.mpatient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * Patient entity, a model class that is mapped to our database and which gathers the patient's data.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "patient")
public class Patient implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
