package com.mnote.service;

import com.mnote.dto.NoteDTO;
import com.mnote.exception.ResourceNotFoundException;
import com.mnote.mapper.NoteMapper;
import com.mnote.model.Note;
import com.mnote.repository.PatientHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientHistoryServiceImplTest {
    private PatientHistoryServiceImpl patientHistoryServiceImpl;
    private Note note;
    private Note note2;
    private List<Note> notes;

    @Mock
    private PatientHistoryRepository patientHistoryRepository;

    @Mock
    private NoteMapper noteMapper;

    @BeforeEach
    public void setUp() {
        patientHistoryServiceImpl = new PatientHistoryServiceImpl(patientHistoryRepository, noteMapper);
        note = new Note("1", 1, "Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois");
        note2 = new Note("2", 1, "Tests de laboratoire indiquant une microalbumine élevée");
        notes = new ArrayList<>();
        notes.add(note);
        notes.add(note2);
    }

    @Test
    @DisplayName("Checking that the note is correctly fetched by its id")
    public void shouldFindPatientNoteByItsId() {
        NoteDTO noteDTO = new NoteDTO(note.getId(), note.getPatId(), note.getE());
        when(patientHistoryRepository.findById("1")).thenReturn(Optional.ofNullable(note));
        when(noteMapper.modelToDto(note)).thenReturn(noteDTO);

        NoteDTO noteToFind = patientHistoryServiceImpl.findById("1");

        verify(patientHistoryRepository).findById(note.getId());
        assertEquals("1", noteToFind.getId());
    }

    @Test
    @DisplayName("Checking that ResourceNotException is thrown when note is not found")
    public void shouldThrowExceptionWhenNoteIsNotFound() {
        doThrow(new ResourceNotFoundException("NoteNotFound", "The id provided is incorrect or does not exist: ", HttpStatus.NOT_FOUND)).when(patientHistoryRepository).findById("0");

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> patientHistoryServiceImpl.findById("0"));

        verify(patientHistoryRepository).findById("0");
        assertEquals(exception.getMessage(), "The id provided is incorrect or does not exist: ");
    }

    @Test
    @DisplayName("Checking that all Mediscreen's notes are correctly fetched")
    public void shouldFindAllNotes() {
        NoteDTO noteDTO = new NoteDTO(note.getId(), note.getPatId(), note.getE());
        NoteDTO noteDTO2 = new NoteDTO(note2.getId(), note2.getPatId(), note2.getE());
        List<NoteDTO> noteDTOList = new ArrayList<>();
        noteDTOList.add(noteDTO);
        noteDTOList.add(noteDTO2);
        when(patientHistoryRepository.findAll()).thenReturn(notes);
        when(noteMapper.modelsToDto(notes)).thenReturn(noteDTOList);

        List<NoteDTO> noteList = patientHistoryServiceImpl.findAllNotes();

        verify(patientHistoryRepository).findAll();
        assertEquals(noteList, noteDTOList);
    }

    @Test
    @DisplayName("Checking that all notes are correctly fetched by the patient's id")
    public void shouldFindPatientHistory() {
        NoteDTO noteDTO = new NoteDTO(note.getId(), note.getPatId(), note.getE());
        NoteDTO noteDTO2 = new NoteDTO(note2.getId(), note2.getPatId(), note2.getE());
        List<NoteDTO> noteDTOList = new ArrayList<>();
        noteDTOList.add(noteDTO);
        noteDTOList.add(noteDTO2);
        when(patientHistoryRepository.findByPatId(1)).thenReturn(notes);
        when(noteMapper.modelsToDto(notes)).thenReturn(noteDTOList);

        List<NoteDTO> noteList = patientHistoryServiceImpl.findByPatId(1);

        verify(patientHistoryRepository).findByPatId(1);
        assertEquals(noteList, noteDTOList);
    }

    @Test
    @DisplayName("Checking that the note is correctly saved")
    public void shouldReturnNewNoteWhenSaved() {
        NoteDTO noteDTO = new NoteDTO("3", 2, "Le patient déclare avoir mal au ventre");
        note = new Note(noteDTO.getId(), noteDTO.getPatId(), noteDTO.getNote());
        when(patientHistoryRepository.save(any(Note.class))).thenReturn(note);
        when(noteMapper.dtoToModel(noteDTO)).thenReturn(note);

        Note noteToSave = patientHistoryServiceImpl.save(noteDTO);

        verify(patientHistoryRepository).save(any(Note.class));
        assertNotNull(noteToSave);
    }

    @Test
    @DisplayName("Checking that the note is updated")
    public void shouldReturnNoteUpdated() {
        note.setE("Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois et avoir le vertige");
        NoteDTO noteDTO = new NoteDTO(note.getId(), note.getPatId(), note.getE());
        when(patientHistoryRepository.findById("1")).thenReturn(Optional.of(note));
        when(patientHistoryRepository.save(any(Note.class))).thenReturn(note);
        when(noteMapper.modelToDto(note)).thenReturn(noteDTO);

        NoteDTO noteUpdated = patientHistoryServiceImpl.update("1", noteDTO);

        verify(patientHistoryRepository).save(any(Note.class));
        assertEquals(note.getE(), noteUpdated.getNote());
    }

    @Test
    @DisplayName("Checking that ResourceNotFoundException is thrown when the note to update is not found")
    public void shouldThrowExceptionWhenNoteToUpdateIsNotFound() {
        NoteDTO noteDTO = new NoteDTO();
        doThrow(new ResourceNotFoundException("PatientHistoryNotFound", "The id provided is incorrect or does not exist: ", HttpStatus.NOT_FOUND)).when(patientHistoryRepository).findById("7");

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> patientHistoryServiceImpl.update("7", noteDTO));

        verify(patientHistoryRepository).findById("7");
        assertEquals(exception.getMessage(), "The id provided is incorrect or does not exist: ");
    }
}
