package com.mdiabetesReport.service;

import com.mdiabetesReport.dto.PatientInfo;
import com.mdiabetesReport.exception.ResourceNotFoundException;
import com.mdiabetesReport.model.Patient;

import java.io.IOException;

public interface DiabetesReportService {
    Patient getPatientById(Integer id) throws ResourceNotFoundException;

    Patient getPatientByFamilyName(String familyName) throws ResourceNotFoundException;

    PatientInfo getPatientInfo(Patient patient) throws IOException;
}
