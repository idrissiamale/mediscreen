package com.mnote.service;

import com.mnote.dto.NoteDTO;
import com.mnote.exception.ResourceNotFoundException;
import com.mnote.model.Note;

import java.util.List;

/**
 * An interface which provides some CRUD methods to implement on PatientHistory service class.
 */
public interface PatientHistoryService {
    /**
     * Retrieves a note by its id.
     *
     * @param id - must not be null.
     * @return the NoteDTO class with the given id.
     * @throws IllegalArgumentException  if id is null.
     * @throws ResourceNotFoundException if id is not found.
     * @see com.mnote.repository.PatientHistoryRepository
     */
    NoteDTO findById(String id) throws ResourceNotFoundException;

    /**
     * Retrieves the notes of the Patient with the given id.
     *
     * @param patId - must not be null. It refers to the patient's id.
     * @return the notes of the patient with the given id.
     * @throws IllegalArgumentException  if id is null.
     * @throws ResourceNotFoundException if id is not found.
     * @see com.mnote.repository.PatientHistoryRepository
     */
    List<NoteDTO> findByPatId(Integer patId) throws ResourceNotFoundException;

    /**
     * Retrieves all notes.
     *
     * @return all notes saved in Mediscreen.
     * @see com.mnote.repository.PatientHistoryRepository
     */
    List<NoteDTO> findAllNotes();

    /**
     * Saves a patient's note.
     *
     * @param noteDTO - must not be null.
     * @return the saved note.
     * @throws IllegalArgumentException if NoteDTO is null.
     * @see com.mnote.repository.PatientHistoryRepository
     */
    Note save(NoteDTO noteDTO) throws IllegalArgumentException;

    /**
     * Updates patient's note.
     *
     * @param id      - note's id. Must not be null.
     * @param noteDTO - must not be null.
     * @return the updated note.
     * @throws ResourceNotFoundException if the patient's note is not found.
     * @see com.mnote.repository.PatientHistoryRepository
     */
    NoteDTO update(String id, NoteDTO noteDTO) throws ResourceNotFoundException;
}
