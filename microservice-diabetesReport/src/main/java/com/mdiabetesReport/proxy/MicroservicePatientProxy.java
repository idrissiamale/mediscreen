package com.mdiabetesReport.proxy;

import com.mdiabetesReport.exception.ResourceNotFoundException;
import com.mdiabetesReport.model.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

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
     * @return the patient with the given id.
     * @throws ResourceNotFoundException if the patient we want to fetch is not found.
     */
    @GetMapping(value = "/patient/{id}")
    Patient getPatientById(@PathVariable("id") Integer id) throws ResourceNotFoundException;

    /**
     * Mapping a GET request in order to fetch the patient with the given name.
     *
     * @param familyName, method parameter which should be bound to the web request parameter.
     * @return the patient with the given name.
     * @throws ResourceNotFoundException if the patient we want to fetch is not found.
     */
    @GetMapping(value = "/patient")
    Patient getPatientByFamilyName(@RequestParam(value = "familyName") String familyName) throws ResourceNotFoundException;

    /**
     * Mapping a GET request in order to fetch patients.
     *
     * @return all patients saved in Mediscreen.
     */
    @GetMapping(value = "/patient/list")
    List<Patient> getAllPatients();
}
