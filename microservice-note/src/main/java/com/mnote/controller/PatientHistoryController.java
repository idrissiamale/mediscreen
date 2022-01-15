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

@RestController
@RequestMapping(value = "/patHistory")
public class PatientHistoryController {
    private static final Logger logger = LogManager.getLogger("PatientHistoryController");
    private PatientHistoryService patientHistoryService;

    @Autowired
    public PatientHistoryController(PatientHistoryService patientHistoryService) {
        this.patientHistoryService = patientHistoryService;
    }

    @GetMapping(value = "/note/{id}")
    public NoteDTO getNote(@PathVariable String id) throws ResourceNotFoundException {
        logger.info("Patient's note found successfully.");
        return patientHistoryService.findById(id);
    }

    @GetMapping("/notes")
    public List<NoteDTO> getAllNotes() {
        logger.info("The notes were successfully fetched.");
        return patientHistoryService.findAllNotes();
    }

    @GetMapping(value = "/notes/{patId}")
    public List<NoteDTO> getPatientHistory(@PathVariable Integer patId) throws ResourceNotFoundException {
        logger.info("Patient's history found successfully.");
        return patientHistoryService.findByPatId(patId);
    }

    @PutMapping(value = "/update/{id}")
    public NoteDTO updatePatientHistory(@PathVariable String id, @Valid @RequestBody NoteDTO noteDTO) throws ResourceNotFoundException {
        logger.info("Note successfully updated for the following patient: " + noteDTO.getPatId());
        return patientHistoryService.update(id, noteDTO);
    }

    @PostMapping(value = "/add")
    public Note addNote(@Valid @RequestBody NoteDTO noteDTO) throws IllegalArgumentException {
        logger.info("Patient's note saved successfully.");
        return patientHistoryService.save(noteDTO);
    }
}
