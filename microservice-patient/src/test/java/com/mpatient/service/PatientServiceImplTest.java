package com.mpatient.service;

import com.mpatient.dto.PatientRegistrationDTO;
import com.mpatient.exception.ResourceNotFoundException;
import com.mpatient.model.Patient;
import com.mpatient.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceImplTest {
    private PatientServiceImpl patientServiceImpl;
    private Patient patient;
    private List<Patient> patients;

    @Mock
    private PatientRepository patientRepository;

    @BeforeEach
    public void setUp() {
        patientServiceImpl = new PatientServiceImpl(patientRepository);
        patient = new Patient(1, "TestBordeline", "Test", "1945-06-24", "M", "2 High St", "200-333-4444");
        Patient patient2 = new Patient(2, "TestInDanger", "Test", "2004-06-18", "M", "3 Club Road", "300-444-5555");
        patients = new ArrayList<>();
        patients.add(patient);
        patients.add(patient2);
    }

    @Test
    @DisplayName("Checking that the patient is correctly fetched by its id")
    public void shouldFindPatientByItsId() {
        when(patientRepository.findById(1)).thenReturn(Optional.ofNullable(patient));

        Patient patientToFind = patientServiceImpl.findById(1);

        verify(patientRepository).findById(patient.getId());
        assertEquals("TestBordeline", patientToFind.getFamily());
    }

    @Test
    @DisplayName("Checking that ResourceNotException is thrown when patient's id does not exist")
    public void shouldThrowExceptionWhenPatientIdDoesNotExist() {
        assertThrows(ResourceNotFoundException.class, () -> patientServiceImpl.findById(0));
    }

    @Test
    @DisplayName("Checking that all patients are correctly fetched")
    public void shouldFindAllPatients() {
        when(patientRepository.findAll()).thenReturn(patients);

        List<Patient> patientList = patientServiceImpl.findAll();

        verify(patientRepository).findAll();
        assertEquals(patientList, patients);
    }

    @Test
    @DisplayName("Checking that the patient user is correctly saved")
    public void shouldReturnNewPatientWhenSaved() {
        PatientRegistrationDTO registration = new PatientRegistrationDTO("TestBordeline", "Test", "1945-06-24", "M", "2 High St", "200-333-4444");
        patient = new Patient(1, registration.getFamily(), registration.getGiven(), registration.getDob(), registration.getSex(), registration.getAddress(), registration.getPhone());
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        Patient patientToSave = patientServiceImpl.save(registration);

        verify(patientRepository).save(any(Patient.class));
        assertNotNull(patientToSave);
    }

    @Test
    @DisplayName("Checking that the patient is updated with the new given name")
    public void shouldReturnPatientWithNewGivenNameWhenPatientUpdated() {
        patient.setGiven("Lili");
        when(patientRepository.findById(1)).thenReturn(Optional.of(patient));
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        Patient patientUpdated = patientServiceImpl.update(1, patient);

        verify(patientRepository).save(any(Patient.class));
        assertEquals("Lili", patientUpdated.getGiven());
    }

    @Test
    @DisplayName("Checking that ResourceNotFoundException is thrown when the patient we want to update is not found")
    public void shouldThrowExceptionWhenPatientToUpdateIsNotFound() {
        doThrow(new ResourceNotFoundException("PatientNotFound", "The id provided is incorrect or does not exist: ", HttpStatus.NOT_FOUND)).when(patientRepository).findById(7);

        assertThrows(ResourceNotFoundException.class, () -> patientServiceImpl.update(7, patient));
        verify(patientRepository).findById(7);
    }
}
