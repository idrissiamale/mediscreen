package com.mpatientHistory.controller;

import com.mpatientHistory.exception.ResourceNotFoundException;
import com.mpatientHistory.model.PatientHistory;
import com.mpatientHistory.service.PatientHistoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Mapping a GET request in order to fetch the history of the patient with the given id.
     *
     * @param id, method parameter which is used as the path variable.
     * @return the history of the patient with the given id.
     * @throws ResourceNotFoundException if the patient's history we want to fetch is not found.
     */
    @GetMapping(value = "/{id}")
    public PatientHistory getPatientHistory(@PathVariable Integer id) throws ResourceNotFoundException {
        logger.info("History found successfully.");
        return patientHistoryService.findById(id);

    }

    /**
     * Mapping a GET request in order to fetch patients' histories.
     *
     * @return all histories saved in Mediscreen.
     */
    @GetMapping(value = "/list")
    public List<PatientHistory> getAllPatientsHistories() {
        logger.info("Patients' histories found successfully.");
        return patientHistoryService.findAll();
    }

    /**
     * Mapping a PUT request in order to update a patient's history.
     *
     * @param id, method parameter which is used as the path variable.
     * @param history,  method parameter which should be bound to the web request body.
     * @return the updated patient's history.
     * @throws ResourceNotFoundException if the patient with the given id is not found.
     */
    @PutMapping(value = "/update/{id}")
    public PatientHistory updatePatient(@PathVariable Integer id, @RequestBody PatientHistory history) throws ResourceNotFoundException {
        logger.info("History successfully updated for the following patient: " + history.getId());
        return patientHistoryService.update(id, history);
    }

    /**
     * Mapping a POST request in order to save a new patient's history.
     *
     * @param history, method parameter which should be bound to the web request body.
     * @return the newly saved history.
     * @throws IllegalArgumentException if the PatientHistory's fields are empty or null.
     */
    @PostMapping(value = "/add")
    public PatientHistory addPatient(@RequestBody PatientHistory history) throws IllegalArgumentException {
        logger.info("Patient's history saved successfully.");
        return patientHistoryService.save(history);
    }
}
