package com.clientui.proxy;

import com.clientui.dto.PatientRegistrationDto;
import com.clientui.exception.BadArgumentException;
import com.clientui.exception.ResourceNotFoundException;
import com.clientui.model.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservice-patient", url = "${mpatient.url}")
public interface MicroservicePatientProxy {
    @GetMapping(value = "/patient/{id}")
    Patient getPatient(@PathVariable("id") Integer id) throws ResourceNotFoundException;

    @GetMapping(value = "/patient/list")
    List<Patient> getAllPatients();

    @PutMapping(value = "/patient/update/{id}")
    Patient updatePatient(@PathVariable("id") Integer id, @RequestBody Patient patient) throws ResourceNotFoundException;

    @PostMapping(value = "/patient/add")
    Patient addPatient(@RequestBody PatientRegistrationDto registration) throws BadArgumentException;
}
