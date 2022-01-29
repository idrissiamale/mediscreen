package com.mdiabetesReport.filter;

import com.mdiabetesReport.dto.NoteDTO;
import com.mdiabetesReport.model.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A filter class we are using to filter notes.
 *
 * @see com.mdiabetesReport.model.Patient
 * @see com.mdiabetesReport.dto.NoteDTO
 */
public class NoteFilter {
    /**
     * Fetching a list of string from notes filtered by the Patient's id.
     *
     * @param notes, it refers to the patient's notes.
     * @param patients, it refers to all the patients of Mediscreen.
     * @return a list of string from notes filtered by the patient's id.
     */
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
