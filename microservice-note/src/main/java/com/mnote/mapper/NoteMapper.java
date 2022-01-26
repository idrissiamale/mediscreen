package com.mnote.mapper;

import com.mnote.dto.NoteDTO;
import com.mnote.model.Note;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapper interface which provides some methods to implement in order to map between Note entity and NoteDTO.
 */
@Mapper(componentModel = "spring")
public interface NoteMapper {
    NoteMapper INSTANCE = Mappers.getMapper(NoteMapper.class);

    @Mapping(source = "note.e", target = "note")
    NoteDTO modelToDto(Note note);

    @Mapping(source = "noteDTO.note", target = "e")
    Note dtoToModel(NoteDTO noteDTO);

    List<NoteDTO> modelsToDto(List<Note> notes);
}
