package com.mnote.controller;

import com.mnote.dto.NoteDTO;
import com.mnote.exception.ResourceNotFoundException;
import com.mnote.model.Note;
import com.mnote.service.PatientHistoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Exposing Note's REST services to other microservices.
 *
 * @see com.mnote.service.PatientHistoryService
 */
@RestController
@RequestMapping(value = "/patHistory")
public class PatientHistoryController {
    private static final Logger logger = LogManager.getLogger("PatientHistoryController");
    private PatientHistoryService patientHistoryService;

    @Autowired
    public PatientHistoryController(PatientHistoryService patientHistoryService) {
        this.patientHistoryService = patientHistoryService;
    }

    /**
     * Mapping a GET request in order to fetch the note with the given id.
     *
     * @param id, method parameter which is used as the path variable.
     * @return the note with the given id.
     * @throws ResourceNotFoundException if the note we want to fetch is not found.
     */
    @GetMapping(value = "/note/{id}")
    public NoteDTO getNote(@PathVariable String id) throws ResourceNotFoundException {
        logger.info("Patient's note found successfully.");
        return patientHistoryService.findById(id);
    }

    /**
     * Mapping a GET request in order to fetch notes.
     *
     * @return all notes saved in Mediscreen.
     */
    @GetMapping("/notes")
    public List<NoteDTO> getAllNotes() {
        logger.info("The notes were successfully fetched.");
        return patientHistoryService.findAllNotes();
    }

    /**
     * Mapping a GET request in order to fetch the notes of the Patient with the given id.
     *
     * @param patId, method parameter which is used as the path variable.
     * @return the notes of the patient with the given id.
     * @throws ResourceNotFoundException if the patient's id is not found.
     * @see com.mnote.repository.PatientHistoryRepository
     */
    @GetMapping(value = "/notes/{patId}")
    public List<NoteDTO> getPatientHistory(@PathVariable Integer patId) throws ResourceNotFoundException {
        logger.info("Patient's history found successfully.");
        return patientHistoryService.findByPatId(patId);
    }

    /**
     * Mapping a PUT request in order to update a patient's note.
     *
     * @param id,      method parameter which is used as the path variable.
     * @param noteDTO, method parameter which should be bound to the web request body.
     * @return the updated patient's note.
     * @throws ResourceNotFoundException if the note with the given id is not found.
     */
    @PutMapping(value = "/update/{id}")
    public NoteDTO updatePatientHistory(@PathVariable String id, @Valid @RequestBody NoteDTO noteDTO) throws ResourceNotFoundException {
        logger.info("Note successfully updated for the following patient: " + noteDTO.getPatId());
        return patientHistoryService.update(id, noteDTO);
    }

    /**
     * Mapping a POST request in order to save a new note.
     *
     * @param noteDTO, method parameter which should be bound to the web request body.
     * @return the newly saved note.
     * @throws IllegalArgumentException if the NoteDTO's fields are empty or null.
     */
    @PostMapping(value = "/add")
    public Note addNote(@Valid @RequestBody NoteDTO noteDTO) throws IllegalArgumentException {
        logger.info("Patient's note saved successfully.");
        return patientHistoryService.save(noteDTO);
    }
}
