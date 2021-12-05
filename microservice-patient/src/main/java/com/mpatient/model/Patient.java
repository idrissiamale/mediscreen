package com.mpatient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    @Column(name = "family", nullable = false)
    private String family;

    @Column(name = "given", nullable = false)
    private String given;

    @Column(name = "dob", nullable = false)
    private String dob;

    @Column(nullable = false)
    private String sex;

    private String address;

    private String phone;
}
