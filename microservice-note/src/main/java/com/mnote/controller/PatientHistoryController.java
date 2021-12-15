package com.mnote.controller;

import com.mnote.dto.PatientHistoryDTO;
import com.mnote.model.PatientHistory;
import com.mnote.service.PatientHistoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/patHistory")
public class PatientHistoryController {
    private static final Logger logger = LogManager.getLogger("PatientHistoryController");
    private PatientHistoryService patientHistoryService;

    @Autowired
    public PatientHistoryController(PatientHistoryService patientHistoryService) {
        this.patientHistoryService = patientHistoryService;
    }

    @PostMapping(value = "/add")
    public PatientHistory addPatientHistory(@Valid @RequestBody PatientHistoryDTO history) throws IllegalArgumentException {
        logger.info("Patient's history saved successfully.");
        return patientHistoryService.save(history);
    }
}
