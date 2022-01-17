package com.clientui.controller;

import com.clientui.dto.NoteDTO;
import com.clientui.model.Note;
import com.clientui.proxy.MicroservicePatientHistoryProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(controllers = PatientHistoryController.class)
public class PatientHistoryControllerTest {
    private MockMvc mockMvc;
    private NoteDTO note;
    private NoteDTO noteUpdated;
    private List<NoteDTO> notes;

    @MockBean
    MicroservicePatientHistoryProxy microservicePatientHistoryProxy;

    @BeforeEach
    public void setup() {
        PatientHistoryController patientHistoryController = new PatientHistoryController(microservicePatientHistoryProxy);
        note = new NoteDTO("1", 1, "Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois");
        NoteDTO note2 = new NoteDTO("2", 1, "Tests de laboratoire indiquant une microalbumine élevée");
        notes = new ArrayList<>();
        notes.add(note);
        notes.add(note2);
        noteUpdated = new NoteDTO(note.getId(), note.getPatId(), "Le patient déclare avoir de la fièvre et des nausées");
        this.mockMvc = MockMvcBuilders.standaloneSetup(patientHistoryController).build();
    }

    @Test
    @DisplayName("Checking that the page of notes is returned when the practitioner makes a GET request to the /patHistory/notes/{patId} URL")
    public void shouldReturnPatientHistoryView() throws Exception {
        when(microservicePatientHistoryProxy.getPatientHistory(note.getPatId())).thenReturn(notes);

        this.mockMvc.perform(get("/patHistory/notes/{patId}", 1)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("patientHistory"))
                .andExpect(model().attribute("notes", notes))
                .andExpect(model().attribute("notes", hasSize(2)))
                .andExpect(model().attribute("notes", hasItem(
                        allOf(
                                hasProperty("id", is("1")),
                                hasProperty("patId", is(1)),
                                hasProperty("note", is("Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois"))
                        )
                )))
                .andExpect(model().attribute("notes", hasItem(
                        allOf(
                                hasProperty("id", is("2")),
                                hasProperty("patId", is(1)),
                                hasProperty("note", is("Tests de laboratoire indiquant une microalbumine élevée"))
                        )
                )));

        verify(microservicePatientHistoryProxy).getPatientHistory(note.getPatId());
    }

    @Test
    @DisplayName("Checking that the patHistory/add page is returned when the practitioner makes a GET request to the /patHistory/add URL")
    public void shouldReturnAddNoteView() throws Exception {
        this.mockMvc.perform(get("/patHistory/add").contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("add_note"));

        verifyNoInteractions(microservicePatientHistoryProxy);
    }

    @Test
    @DisplayName("Checking that the practitioner is redirected to the patient's history page when note is correctly saved")
    public void shouldReturnPatientHistoryViewWhenNoteIsCorrectlySaved() throws Exception {
        Note noteToSave = new Note(note.getId(), note.getPatId(), note.getNote());
        when(microservicePatientHistoryProxy.addNote(any(NoteDTO.class))).thenReturn(noteToSave);

        this.mockMvc.perform(post("/patHistory/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", note.getId())
                .param("patId", String.valueOf(note.getPatId()))
                .param("note", note.getNote())
                .sessionAttr("noteDTO", note)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/patHistory/notes/" + note.getPatId()))
                .andExpect(redirectedUrl("/patHistory/notes/" + note.getPatId()))
                .andExpect(model().hasNoErrors());

        verify(microservicePatientHistoryProxy).addNote(any(NoteDTO.class));
        assertEquals(1, note.getPatId());
    }

    @Test
    @DisplayName("Checking that the patHistory/add page returns error message when there are errors on patient's notes")
    public void shouldReturnAddNoteFormWhenErrorsOnNoteField() throws Exception {
        String emptyNote = " ";

        this.mockMvc.perform(post("/patHistory/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", note.getId())
                .param("patId", String.valueOf(note.getPatId()))
                .param("note", emptyNote)
                .sessionAttr("noteDTO", note)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("add_note"))
                .andExpect(model().attributeHasFieldErrors("noteDTO", "note"))
                .andExpect(model().attribute("noteDTO", hasProperty("note", is(emptyNote))));

        verifyNoInteractions(microservicePatientHistoryProxy);
    }

    @Test
    @DisplayName("Checking that the 'update_note' form  is returned when the practitioner makes a GET request to the /patHistory/update/{id} URL")
    public void shouldReturnUpdateNoteFormView() throws Exception {
        when(microservicePatientHistoryProxy.getNote("1")).thenReturn(note);

        this.mockMvc.perform(get("/patHistory/update/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("update_note"))
                .andExpect(model().attribute("noteDTO", note));

        verify(microservicePatientHistoryProxy).getNote("1");
    }

    @Test
    @DisplayName("Checking that the practitioner is redirected to the patient's history page when the note is correctly updated")
    public void shouldReturnPatientHistoryPageWhenNoteIsCorrectlyUpdated() throws Exception {
        when(microservicePatientHistoryProxy.updatePatientHistory(anyString(), any(NoteDTO.class))).thenReturn(noteUpdated);

        this.mockMvc.perform(post("/patHistory/update/{id}", 1).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", noteUpdated.getId())
                .param("patId", String.valueOf(noteUpdated.getPatId()))
                .param("note", noteUpdated.getNote())
                .sessionAttr("noteDTO", noteUpdated)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/patHistory/notes/" + noteUpdated.getPatId()))
                .andExpect(redirectedUrl("/patHistory/notes/" + noteUpdated.getPatId()))
                .andExpect(model().hasNoErrors());

        verify(microservicePatientHistoryProxy).updatePatientHistory(anyString(), any(NoteDTO.class));
        assertEquals("Le patient déclare avoir de la fièvre et des nausées", noteUpdated.getNote());
    }

    @Test
    @DisplayName("Checking that the 'update_note' form is returned with error message when there are errors on the form fields")
    public void shouldReturnUpdateNoteFormWhenErrorsOnNoteField() throws Exception {
        String emptyNote = "";

        this.mockMvc.perform(post("/patHistory/update/{id}", 1).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", noteUpdated.getId())
                .param("patId", String.valueOf(noteUpdated.getPatId()))
                .param("note", emptyNote)
                .sessionAttr("noteDTO", noteUpdated)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("update_note"))
                .andExpect(model().attributeHasFieldErrors("noteDTO", "note"))
                .andExpect(model().attribute("noteDTO", hasProperty("note", is(emptyNote))));

        verifyNoInteractions(microservicePatientHistoryProxy);
    }
}
