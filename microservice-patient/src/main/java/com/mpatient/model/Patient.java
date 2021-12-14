package com.mpatient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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
