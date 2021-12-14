package com.mpatient.service;

import com.mpatient.dto.PatientRegistrationDTO;
import com.mpatient.exception.ResourceNotFoundException;
import com.mpatient.model.Patient;
import com.mpatient.repository.PatientRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the PatientService interface.
 *
 * @see PatientService
 */
@Service
public class PatientServiceImpl implements PatientService {
    private static final Logger logger = LogManager.getLogger("PatientServiceImpl");
    private PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Patient findById(Integer id) throws ResourceNotFoundException {
        logger.info("Patient was successfully fetched.");
        return patientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("PatientNotFound", "The id provided is incorrect or does not exist: " + id, HttpStatus.NOT_FOUND));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Patient> findAll() {
        logger.info("Patients data were successfully fetched.");
        return patientRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Patient save(PatientRegistrationDTO registration) throws IllegalArgumentException {
        Patient patient = new Patient();
        patient.setFamily(registration.getFamily());
        patient.setGiven(registration.getGiven());
        patient.setDob(registration.getDob());
        patient.setSex(registration.getSex());
        patient.setAddress(registration.getAddress());
        patient.setPhone(registration.getPhone());
        logger.info("Patient was saved successfully.");
        return patientRepository.save(patient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Patient update(Integer id, Patient patient) throws ResourceNotFoundException {
        return patientRepository.findById(id).map(patientToUpdate -> {
            patientToUpdate.setFamily(patient.getFamily());
            patientToUpdate.setGiven(patient.getGiven());
            patientToUpdate.setDob(patient.getDob());
            patientToUpdate.setSex(patient.getSex());
            patientToUpdate.setAddress(patient.getAddress());
            patientToUpdate.setPhone(patient.getPhone());
            logger.info("Patient data were updated successfully.");
            return patientRepository.save(patientToUpdate);
        }).orElseThrow(() -> new ResourceNotFoundException("PatientNotFound", "The id provided is incorrect or does not exist: " + id, HttpStatus.NOT_FOUND));
    }
}
