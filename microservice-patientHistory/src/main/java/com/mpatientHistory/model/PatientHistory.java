package com.mpatientHistory.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Document(collection = "PatientHistory")
public class PatientHistory {
    @Id
    private Integer id;

    @NotNull
    private String practitionerNote;
}
