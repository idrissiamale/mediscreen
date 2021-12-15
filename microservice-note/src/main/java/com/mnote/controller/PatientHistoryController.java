package com.mnote.controller;

import com.mnote.dto.PatientHistoryDTO;
import com.mnote.exception.ResourceNotFoundException;
import com.mnote.model.PatientHistory;
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
    public PatientHistory getPatient(@PathVariable Integer id) throws ResourceNotFoundException {
        logger.info("Patient's history found successfully.");
        return patientHistoryService.findById(id);

    }

    @GetMapping(value = "/list")
    public List<PatientHistory> getAllPatients() {
        logger.info("Patients' histories found successfully.");
        return patientHistoryService.findAll();
    }

    @PutMapping(value = "/update/{id}")
    public PatientHistory updatePatient(@PathVariable Integer id, @Valid @RequestBody PatientHistory history) throws ResourceNotFoundException {
        logger.info("History successfully updated for the following patient: " + history.getPatId());
        return patientHistoryService.update(id, history);
    }

    @PostMapping(value = "/add")
    public PatientHistory addPatientHistory(@Valid @RequestBody PatientHistoryDTO history) throws IllegalArgumentException {
        logger.info("Patient's history saved successfully.");
        return patientHistoryService.save(history);
    }
}
