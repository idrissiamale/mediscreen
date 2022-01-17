package com.mdiabetesReport.filter;

import com.mdiabetesReport.dto.NoteDTO;
import com.mdiabetesReport.model.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NoteFilterTest {
    private List<NoteDTO> notes;
    private List<Patient> patients;

    @BeforeEach
    public void setUp() {
        NoteDTO note = new NoteDTO("1", 1, "Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois");
        NoteDTO note2 = new NoteDTO("2", 1, "Tests de laboratoire indiquant une microalbumine élevée");
        Patient patient = new Patient(1, "TestBorderline", "Test", "1945-06-24", "M");
        notes = new ArrayList<>();
        notes.add(note);
        notes.add(note2);
        patients = new ArrayList<>();
        patients.add(patient);
    }

    @Test
    @DisplayName("Checking that the static method returns correctly notes filtered by patient")
    public void shouldReturnFilteredNotesByPatient() {
        List<String> filteredNotes = NoteFilter.getNotesFilteredByPatient(notes, patients);
        assertEquals(2, filteredNotes.size());
    }
}
