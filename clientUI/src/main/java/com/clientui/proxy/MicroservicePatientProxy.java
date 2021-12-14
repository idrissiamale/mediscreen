package com.clientui.proxy;

import com.clientui.dto.PatientRegistrationDTO;
import com.clientui.exception.ResourceNotFoundException;
import com.clientui.model.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * An interface which permits to consume, with Feign client, the REST services exposed by Patient microservice.
 */
@FeignClient(name = "microservice-patient", url = "${mpatient.url}")
public interface MicroservicePatientProxy {
    /**
     * Mapping a GET request in order to fetch the patient with the given id.
     *
     * @param id, method parameter which is used as the path variable.
     * @return the patient with the given name.
     * @throws ResourceNotFoundException if the patient we want to fetch is not found.
     */
    @GetMapping(value = "/patient/{id}")
    Patient getPatient(@PathVariable("id") Integer id) throws ResourceNotFoundException;

    /**
     * Mapping a GET request in order to fetch patients.
     *
     * @return all patients saved in Mediscreen.
     */
    @GetMapping(value = "/patient/list")
    List<Patient> getAllPatients();

    /**
     * Mapping a PUT request in order to update a patient's data.
     *
     * @param id, method parameter which is used as the path variable.
     * @param patient,  method parameter which should be bound to the web request body.
     * @return the updated patient's data.
     * @throws ResourceNotFoundException if the patient with the given id is not found.
     */
    @PutMapping(value = "/patient/update/{id}")
    Patient updatePatient(@PathVariable("id") Integer id, @Valid @RequestBody Patient patient) throws ResourceNotFoundException;

    /**
     * Mapping a POST request in order to save a new patient.
     *
     * @param registration, method parameter which should be bound to the web request body.
     * @return the newly saved patient.
     * @throws IllegalArgumentException if the PatientRegistrationDto's fields are empty or null.
     */
    @PostMapping(value = "/patient/add")
    Patient addPatient(@Valid @RequestBody PatientRegistrationDTO registration) throws IllegalArgumentException;
}
