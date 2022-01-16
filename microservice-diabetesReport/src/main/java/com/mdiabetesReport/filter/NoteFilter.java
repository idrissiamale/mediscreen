package com.mdiabetesReport.filter;

import com.mdiabetesReport.dto.NoteDTO;
import com.mdiabetesReport.model.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NoteFilter {
    public static List<String> getNotesFilteredByPatient(List<NoteDTO> notes, List<Patient> patients) {
        List<String> practitionerNotes = new ArrayList<>();
        for (Patient patient : patients) {
            List<String> patientNotes = notes.stream()
                    .filter(noteDTO -> noteDTO.getPatId().equals(patient.getId()))
                    .map(NoteDTO::getNote)
                    .collect(Collectors.toCollection(ArrayList::new));
            practitionerNotes.addAll(patientNotes);
        }
        return practitionerNotes;
    }
}
