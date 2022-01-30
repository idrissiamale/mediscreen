package com.mdiabetesReport.service;

import com.mdiabetesReport.dto.PatientInfo;
import com.mdiabetesReport.exception.ResourceNotFoundException;
import com.mdiabetesReport.model.Patient;

import java.io.IOException;

public interface DiabetesReportService {
    /**
     * Retrieves a patient by his/her id.
     *
     * @param id - must not be null.
     * @return the Patient entity with the given id.
     * @throws IllegalArgumentException  if id is null.
     * @throws ResourceNotFoundException if id is not found.
     * @see com.mdiabetesReport.proxy.MicroservicePatientProxy
     */
    Patient getPatientById(Integer id) throws ResourceNotFoundException;

    /**
     * Retrieves a patient by his/her family name.
     *
     * @param familyName - must not be null.
     * @return the Patient entity with the given name.
     * @throws IllegalArgumentException  if family name is null.
     * @throws ResourceNotFoundException if family name is not found.
     * @see com.mdiabetesReport.proxy.MicroservicePatientProxy
     */
    Patient getPatientByFamilyName(String familyName) throws ResourceNotFoundException;

    /**
     * Retrieves the patient's data including his/her diabetes level.
     *
     * @param patient - the Patient entity. Must not be null.
     * @return the PatientInfo DTO class with the patient's medical data.
     * @throws IllegalArgumentException if the entity is null.
     * @throws IOException              if an I/O error occurs reading from the Keywords file.
     * @see com.mdiabetesReport.dto.PatientInfo
     */
    PatientInfo getPatientInfo(Patient patient) throws IOException;
}
