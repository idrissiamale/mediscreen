package com.clientui.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class Note {
    @NotNull
    private String id;

    @NotNull(message = "Please enter the id of the patient")
    private Integer patId;

    @NotBlank(message = "Please add your notes or recommendations")
    private String e;
}
