package com.mnote.mapper;

import com.mnote.dto.NoteDTO;
import com.mnote.exception.ResourceNotFoundException;
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

    /**
     * Converts Note entity to NoteDTO class.
     *
     * @param note - must not be null.
     * @return the NoteDTO class.
     */
    @Mapping(source = "note.e", target = "note")
    NoteDTO modelToDto(Note note);

    /**
     * Converts NoteDTO to Note entity.
     *
     * @param noteDTO - must not be null.
     * @return the Note entity.
     */
    @Mapping(source = "noteDTO.note", target = "e")
    Note dtoToModel(NoteDTO noteDTO);

    /**
     * Converts a list of notes to a list of NoteDTOs.
     *
     * @param notes - must not be null.
     * @return the list of NoteDTO objects.
     */
    List<NoteDTO> modelsToDto(List<Note> notes);
}
