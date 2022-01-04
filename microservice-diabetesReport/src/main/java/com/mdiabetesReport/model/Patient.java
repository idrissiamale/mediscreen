package com.mdiabetesReport.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Patient entity, a model class that is mapped to our database and which gathers the patient's data.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient implements Serializable {
    private static final long serialVersionUID = 1L;

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
    private String sex;

    private String address;

    private String phone;
}
