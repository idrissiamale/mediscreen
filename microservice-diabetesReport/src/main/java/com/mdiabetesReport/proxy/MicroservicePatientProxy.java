package com.mdiabetesReport.proxy;

import com.mdiabetesReport.exception.ResourceNotFoundException;
import com.mdiabetesReport.model.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservice-patient", url = "${mpatient.url}")
public interface MicroservicePatientProxy {
    @GetMapping(value = "/patient/{id}")
    Patient getPatientById(@PathVariable("id") Integer id) throws ResourceNotFoundException;

    @GetMapping(value = "/patient")
    Patient getPatientByFamilyName(@RequestParam(value="familyName") String familyName) throws ResourceNotFoundException;

    @GetMapping(value = "/patient/list")
    List<Patient> getAllPatients();
}
