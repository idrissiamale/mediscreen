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
    public Note getNote(@PathVariable String id) throws ResourceNotFoundException {
        logger.info("Patient's note found successfully.");
        return patientHistoryService.findById(id);
    }

    @GetMapping(value = "/notes/{patId}")
    public List<Note> getPatientHistory(@PathVariable Integer patId) throws ResourceNotFoundException {
        logger.info("Patient's history found successfully.");
        return patientHistoryService.findByPatId(patId);

    }

    @PutMapping(value = "/update/{id}")
    public Note updatePatientHistory(@PathVariable String id, @Valid @RequestBody Note note) throws ResourceNotFoundException {
        logger.info("Note successfully updated for the following patient: " + note.getPatId());
        return patientHistoryService.update(id, note);
    }

    @PostMapping(value = "/add")
    public Note addNote(@Valid @RequestBody NoteDTO note) throws IllegalArgumentException {
        logger.info("Patient's note saved successfully.");
        return patientHistoryService.save(note);
    }
}
