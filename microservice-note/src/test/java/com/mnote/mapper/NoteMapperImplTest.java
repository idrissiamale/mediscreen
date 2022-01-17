package com.mnote.mapper;

import com.mnote.dto.NoteDTO;
import com.mnote.model.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NoteMapperImplTest {
    private Note note;
    private NoteDTO noteDTO;
    private List<Note> notes;
    private List<NoteDTO> noteDTOList;

    private NoteMapperImpl noteMapperImpl;

    @BeforeEach
    public void setUp() {
        noteMapperImpl = new NoteMapperImpl();
        note = new Note("1", 1, "Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois");
        noteDTO = new NoteDTO(note.getId(), note.getPatId(), note.getE());
        notes = new ArrayList<>();
        noteDTOList = new ArrayList<>();
    }

    @Test
    @DisplayName("Checking that the note is converted to DTO class")
    public void shouldConvertNoteToNoteDTO() {
        NoteDTO noteMapped = noteMapperImpl.modelToDto(note);

        assertEquals(noteDTO.getId(), noteMapped.getId());
    }

    @Test
    @DisplayName("Checking that the NoteDTO is converted to the model class")
    public void shouldConvertNoteDTOToNote() {
        Note noteMapped = noteMapperImpl.dtoToModel(noteDTO);

        assertEquals(note.getId(), noteMapped.getId());
    }

    @Test
    @DisplayName("Checking that the list of notes is converted to the DTO list")
    public void shouldConvertNotesToListOfDTO() {
        Note note2 = new Note("2", 1, "Tests de laboratoire indiquant une microalbumine élevée");
        NoteDTO noteDTO2 = new NoteDTO(note2.getId(), note2.getPatId(), note2.getE());
        notes.add(note);
        notes.add(note2);
        noteDTOList.add(noteDTO);
        noteDTOList.add(noteDTO2);

        List<NoteDTO> notesMapped = noteMapperImpl.modelsToDto(notes);

        assertEquals(noteDTOList.size(), notesMapped.size());
    }
}
