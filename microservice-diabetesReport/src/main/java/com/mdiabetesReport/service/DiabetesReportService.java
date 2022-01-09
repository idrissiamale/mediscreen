package com.mdiabetesReport.service;

import com.mdiabetesReport.exception.ResourceNotFoundException;
import com.mdiabetesReport.model.Patient;
import com.mdiabetesReport.dto.NoteDTO;

import java.io.IOException;
import java.util.List;

public interface DiabetesReportService {
    List<Patient> getAllPatients();

    List<NoteDTO> getPatientNotes(Integer patId) throws ResourceNotFoundException;

    int searchAndCountKeywords(Integer patId) throws IOException;
}
