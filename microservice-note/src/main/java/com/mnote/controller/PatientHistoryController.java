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
@RequestMapping("/patHistory")
public class PatientHistoryController {
    private static final Logger logger = LogManager.getLogger("PatientHistoryController");
    private PatientHistoryService patientHistoryService;

    @Autowired
    public PatientHistoryController(PatientHistoryService patientHistoryService) {
        this.patientHistoryService = patientHistoryService;
    }

    @GetMapping(value = "/{id}")
    public List<Note> getPatientHistory(@PathVariable Integer id) throws ResourceNotFoundException {
        logger.info("Patient's history found successfully.");
        return patientHistoryService.findByPatId(id);

    }

    @GetMapping(value = "/list")
    public List<Note> getAllPatients() {
        logger.info("Patients' histories found successfully.");
        return patientHistoryService.findAll();
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
