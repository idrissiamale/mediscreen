package com.clientui.mapper;

import com.clientui.dto.NoteDTO;
import com.clientui.model.Note;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-12-28T18:58:41+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_251 (Oracle Corporation)"
)
@Component
public class NoteMapperImpl implements NoteMapper {

    @Override
    public NoteDTO modelToDto(Note note) {
        if ( note == null ) {
            return null;
        }

        NoteDTO noteDTO = new NoteDTO();

        noteDTO.setNote( note.getE() );
        noteDTO.setId( note.getId() );
        noteDTO.setPatId( note.getPatId() );

        return noteDTO;
    }

    @Override
    public Note dtoToModel(NoteDTO noteDTO) {
        if ( noteDTO == null ) {
            return null;
        }

        Note note = new Note();

        note.setE( noteDTO.getNote() );
        note.setId( noteDTO.getId() );
        note.setPatId( noteDTO.getPatId() );

        return note;
    }

    @Override
    public List<NoteDTO> modelsToDto(List<Note> notes) {
        if ( notes == null ) {
            return null;
        }

        List<NoteDTO> list = new ArrayList<NoteDTO>( notes.size() );
        for ( Note note : notes ) {
            list.add( modelToDto( note ) );
        }

        return list;
    }
}