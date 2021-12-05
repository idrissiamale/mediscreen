package com.mpatient.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpatient.controller.PatientController;
import com.mpatient.dto.PatientRegistrationDto;
import com.mpatient.model.Patient;
import com.mpatient.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PatientController.class)
public class PatientControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper;
    private Patient patient;
    private List<Patient> patients;

    @MockBean
    PatientService patientService;

    @BeforeEach
    public void setUpPerTest() {
        patient = new Patient(1, "TestBordeline", "Test", "1945-06-24", "M", "2 High St", "200-333-4444");
        Patient patient2 = new Patient(2, "TestInDanger", "Test", "2004-06-18", "M", "3 Club Road", "300-444-5555");
        patients = new ArrayList<>();
        patients.add(patient);
        patients.add(patient2);
        mapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Checking that the controller returns status code 200 when the patient is correctly fetched")
    public void shouldReturn200WhenPatientIsFound() throws Exception {
        String jsonContent = mapper.writeValueAsString(patient);
        when(patientService.findById(patient.getId())).thenReturn(patient);

        mockMvc
                .perform(get("/patient/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());

        verify(patientService).findById(patient.getId());
    }

    @Test
    @DisplayName("Checking that the controller returns status code 200 when when persons are correctly fetched")
    public void shouldReturn200WhenPatientsAreFound() throws Exception {
        String jsonContent = mapper.writeValueAsString(patients);
        when(patientService.findAll()).thenReturn(patients);

        mockMvc
                .perform(get("/patient/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());

        verify(patientService).findAll();
    }

    @Test
    @DisplayName("Checking that the controller returns status code 200 when when patient's data were correctly updated")
    public void shouldReturn200WhenPatientDataUpdated() throws Exception {
        Patient patientUpdated = new Patient(1, "TestBordeline", "Lili", "1945-06-24", "M", "2 High St", "200-333-4444");
        String jsonContent = mapper.writeValueAsString(patientUpdated);
        when(patientService.update(patient.getId(), patient)).thenReturn(patientUpdated);

        mockMvc
                .perform(put("/patient/update/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());

        verify(patientService).update(anyInt(), any(Patient.class));
    }

    @Test
    @DisplayName("Checking that the controller returns status code 201 when person is correctly saved")
    public void shouldReturn201WhenPersonIsSaved() throws Exception {
        PatientRegistrationDto registration = new PatientRegistrationDto("Joanna", "Loan", "2000-06-24", "F", "Bordeaux", "000000000");
        Patient patientToSave = new Patient(3, registration.getFamily(), registration.getGiven(), registration.getDob(), registration.getSex(), registration.getAddress(), registration.getPhone());
        String jsonContent = mapper.writeValueAsString(patientToSave);
        when(patientService.save(any(PatientRegistrationDto.class))).thenReturn(patientToSave);

        mockMvc
                .perform(post("/patient/add").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());

        verify(patientService).save(any(PatientRegistrationDto.class));
    }
}
