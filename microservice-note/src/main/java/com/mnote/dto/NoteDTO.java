package com.mnote.dto;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NoteDTO {
    @Id
    private String id;

    @NotNull
    private Integer patId;

    @NotNull
    private String note;
}
