package com.mpatient.service;


import com.mpatient.dto.PatientRegistrationDTO;
import com.mpatient.exception.ResourceNotFoundException;
import com.mpatient.model.Patient;

import java.util.List;

/**
 * An interface which provides some CRUD methods to implement on Patient service class.
 */
public interface PatientService {
    /**
     * Retrieves a patient by its id.
     *
     * @param id - must not be null.
     * @return the Patient entity with the given id or Optional#empty() if none found.
     * @throws ResourceNotFoundException if id is not found.
     * @see com.mpatient.repository.PatientRepository
     */
    Patient findById(Integer id) throws ResourceNotFoundException;

    /**
     * Retrieves all patients.
     *
     * @return all Patient entities.
     * @see com.mpatient.repository.PatientRepository
     */
    List<Patient> findAll();

    /**
     * Saves a patient.
     *
     * @param patient - must not be null.
     * @return the saved patient.
     * @throws IllegalArgumentException if Patient entity is null.
     * @see com.mpatient.repository.PatientRepository
     */
    Patient save(PatientRegistrationDTO patient) throws IllegalArgumentException;


    /**
     * Updates patient's data.
     *
     * @param id - patient's id. Must not be null.
     * @param patient - must not be null.
     * @return the updated patient.
     * @throws ResourceNotFoundException if Patient is not found.
     * @see com.mpatient.repository.PatientRepository
     */
    Patient update(Integer id, Patient patient) throws ResourceNotFoundException;
}
