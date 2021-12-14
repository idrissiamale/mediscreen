package com.mpatient.controller;


import com.mpatient.dto.PatientRegistrationDTO;
import com.mpatient.exception.ResourceNotFoundException;
import com.mpatient.model.Patient;
import com.mpatient.service.PatientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Exposing Patient's REST services to other microservices.
 *
 * @see com.mpatient.service.PatientService
 */
@RestController
@RequestMapping("/patient")
public class PatientController {
    private static final Logger logger = LogManager.getLogger("PatientController");
    private PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * Mapping a GET request in order to fetch the patient with the given id.
     *
     * @param id, method parameter which is used as the path variable.
     * @return the patient with the given id.
     * @throws ResourceNotFoundException if the patient we want to fetch is not found.
     */
    @GetMapping(value = "/{id}")
    public Patient getPatient(@PathVariable Integer id) throws ResourceNotFoundException {
        logger.info("Patient found successfully.");
        return patientService.findById(id);

    }

    /**
     * Mapping a GET request in order to fetch patients.
     *
     * @return all patients saved in Mediscreen.
     */
    @GetMapping(value = "/list")
    public List<Patient> getAllPatients() {
        logger.info("Patients data found successfully.");
        return patientService.findAll();
    }

    /**
     * Mapping a PUT request in order to update a patient's data.
     *
     * @param id, method parameter which is used as the path variable.
     * @param patient,  method parameter which should be bound to the web request body.
     * @return the updated patient's data.
     * @throws ResourceNotFoundException if the patient with the given id is not found.
     */
    @PutMapping(value = "/update/{id}")
    public Patient updatePatient(@PathVariable Integer id, @Valid @RequestBody Patient patient) throws ResourceNotFoundException {
        logger.info("Data successfully updated for the following patient: " + patient.getFamily() + " " + patient.getGiven());
        return patientService.update(id, patient);
    }

    /**
     * Mapping a POST request in order to save a new patient.
     *
     * @param registration, method parameter which should be bound to the web request body.
     * @return the newly saved patient.
     * @throws IllegalArgumentException if the PatientRegistrationDto's fields are empty or null.
     */
    @PostMapping(value = "/add")
    public Patient addPatient(@Valid @RequestBody PatientRegistrationDTO registration) throws IllegalArgumentException {
        logger.info("Patient saved successfully.");
        return patientService.save(registration);
    }
}
