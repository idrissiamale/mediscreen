package com.mnote.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mnote.dto.NoteDTO;
import com.mnote.model.Note;
import com.mnote.service.PatientHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PatientHistoryController.class)
public class PatientHistoryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper;
    private NoteDTO note;
    private List<NoteDTO> notes;

    @MockBean
    PatientHistoryService patientHistoryService;

    @BeforeEach
    public void setUpPerTest() {
        note = new NoteDTO("1", 1, "Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois");
        NoteDTO note2 = new NoteDTO("2", 1, "Tests de laboratoire indiquant une microalbumine élevée");
        notes = new ArrayList<>();
        notes.add(note);
        notes.add(note2);
        mapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Checking that the controller returns status code 200 when the note is correctly fetched by its id")
    public void shouldReturn200WhenNoteIsFoundByItsId() throws Exception {
        String jsonContent = mapper.writeValueAsString(note);
        when(patientHistoryService.findById(note.getId())).thenReturn(note);

        mockMvc
                .perform(get("/patHistory/note/{id}", note.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());

        verify(patientHistoryService).findById(note.getId());
    }

    @Test
    @DisplayName("Checking that the controller returns status code 200 when all notes are correctly fetched")
    public void shouldReturn200WhenAllNotesAreFound() throws Exception {
        String jsonContent = mapper.writeValueAsString(notes);
        when(patientHistoryService.findAllNotes()).thenReturn(notes);

        mockMvc
                .perform(get("/patHistory/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());

        verify(patientHistoryService).findAllNotes();
    }

    @Test
    @DisplayName("Checking that the controller returns status code 200 when all the patient's notes are correctly fetched by his/her id")
    public void shouldReturn200WhenAllPatientNotesAreFoundById() throws Exception {
        String jsonContent = mapper.writeValueAsString(notes);
        when(patientHistoryService.findByPatId(note.getPatId())).thenReturn(notes);

        mockMvc
                .perform(get("/patHistory/notes/{patId}", note.getPatId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());

        verify(patientHistoryService).findByPatId(note.getPatId());
    }

    @Test
    @DisplayName("Checking that the controller returns status code 200  when patient's notes were correctly updated")
    public void shouldReturn200WhenPatientNotesUpdated() throws Exception {
        NoteDTO noteUpdated = new NoteDTO("1", 1, "Le patient déclare avoir le vertige et des nausées");
        String jsonContent = mapper.writeValueAsString(noteUpdated);
        when(patientHistoryService.update(note.getId(), note)).thenReturn(noteUpdated);

        mockMvc
                .perform(put("/patHistory/update/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());

        verify(patientHistoryService).update(anyString(), any(NoteDTO.class));
    }

    @Test
    @DisplayName("Checking that the controller returns status code 400 when there are errors on the data of the note we want to update")
    public void shouldReturn400WhenErrorsOnDataOfNoteToUpdate() throws Exception {
        NoteDTO noteUpdated = new NoteDTO("1", 1, null);
        String jsonContent = mapper.writeValueAsString(noteUpdated);
        when(patientHistoryService.update(note.getId(), note)).thenReturn(noteUpdated);

        mockMvc
                .perform(put("/patHistory/update/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(patientHistoryService);
    }

    @Test
    @DisplayName("Checking that the controller returns status code 200 when note is correctly saved")
    public void shouldReturn200WhenNoteIsSaved() throws Exception {
        NoteDTO noteDTO = new NoteDTO("3", 2, "Le patient déclare avoir mal au ventre");
        Note noteToSave = new Note(noteDTO.getId(), noteDTO.getPatId(), noteDTO.getNote());
        String jsonContent = mapper.writeValueAsString(noteDTO);
        when(patientHistoryService.save(any(NoteDTO.class))).thenReturn(noteToSave);

        mockMvc
                .perform(post("/patHistory/add").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());

        verify(patientHistoryService).save(any(NoteDTO.class));
    }

    @Test
    @DisplayName("Checking that the controller returns status code 400 when there are errors on Note's fields")
    public void shouldReturn400WhenErrorOnPatientFields() throws Exception {
        NoteDTO noteDTO = new NoteDTO("3", null, "Le patient déclare avoir mal au ventre");
        Note noteToSave = new Note(noteDTO.getId(), noteDTO.getPatId(), noteDTO.getNote());
        String jsonContent = mapper.writeValueAsString(noteDTO);
        when(patientHistoryService.save(any(NoteDTO.class))).thenReturn(noteToSave);

        mockMvc
                .perform(post("/patHistory/add").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(patientHistoryService);
    }
}
