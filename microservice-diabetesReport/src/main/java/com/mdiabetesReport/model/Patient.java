package com.mdiabetesReport.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Patient entity, a model class that is mapped to our database and which gathers the patient's data.
 */
@Getter
@AllArgsConstructor
public class Patient implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String family;

    private String given;

    private String dob;

    private String sex;
}
