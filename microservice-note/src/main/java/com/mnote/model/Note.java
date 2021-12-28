package com.mnote.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Document(collection = "PatientHistory")
public class Note {
    @Id
    @Field("_id")
    private String id;

    @NotNull
    private Integer patId;

    @NotNull
    private String e;
}
