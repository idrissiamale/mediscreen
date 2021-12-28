package com.clientui.mapper;


import com.clientui.dto.NoteDTO;
import com.clientui.model.Note;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NoteMapper {
    NoteMapper INSTANCE = Mappers.getMapper(NoteMapper.class);

    @Mapping(source = "note.e", target = "note")
    NoteDTO modelToDto(Note note);

    @Mapping(source = "noteDTO.note", target = "e")
    Note dtoToModel(NoteDTO noteDTO);

    List<NoteDTO> modelsToDto(List<Note> notes);
}
