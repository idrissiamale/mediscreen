package com.mnote.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class NoteDTO {
    @Id
    private String id;

    @NotNull
    private Integer patId;

    @NotNull
    private String note;
}
