package com.mpatientHistory.service;

import com.mpatientHistory.model.PatientHistory;

import java.util.List;

/**
 * An interface which provides some CRUD methods to implement on PatientHistory service class.
 */
public interface PatientHistoryService {
    /**
     * Retrieves a patient by its id.
     *
     * @param id - must not be null.
     * @return the Patient entity with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException if id is null or not found.
     * @see com.mpatientHistory.repository.PatientHistoryRepository
     */
    PatientHistory findById(Integer id) throws IllegalArgumentException;

    /**
     * Retrieves all patients.
     *
     * @return all Patient entities.
     * @see com.mpatientHistory.repository.PatientHistoryRepository
     */
    List<PatientHistory> findAll();

    /**
     * Saves a patient.
     *
     * @param history - must not be null.
     * @return the saved patient.
     * @throws IllegalArgumentException if Patient entity is null.
     * @see com.mpatientHistory.repository.PatientHistoryRepository
     */
    PatientHistory save(PatientHistory history) throws IllegalArgumentException;


    /**
     * Updates patient's data.
     *
     * @param id - patient's id. Must not be null.
     * @param history - must not be null.
     * @return the updated patient.
     * @throws IllegalArgumentException if Patient entity/id is null or not found.
     * @see com.mpatientHistory.repository.PatientHistoryRepository
     */
    PatientHistory update(Integer id, PatientHistory history) throws IllegalArgumentException;
}
