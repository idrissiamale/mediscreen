package com.mdiabetesReport.controller;

import com.mdiabetesReport.dto.PatientInfo;
import com.mdiabetesReport.model.Patient;
import com.mdiabetesReport.service.DiabetesReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.mdiabetesReport.helper.HelperClass.getAge;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DiabetesReportController.class)
public class DiabetesReportControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private Patient patient;

    @MockBean
    DiabetesReportService diabetesReportService;

    @BeforeEach
    public void setUpPerTest() {
        patient = new Patient(1, "TestBorderline", "Test", "1945-06-24", "M");
    }

    @Test
    @DisplayName("Checking that the controller returns status code 200 and the patient's info when he/she is correctly fetched by his/her id")
    public void shouldReturn200AndInfoWhenPatientIsFoundById() throws Exception {
        when(diabetesReportService.getPatientById(patient.getId())).thenReturn(patient);
        PatientInfo patientInfo = new PatientInfo(patient.getId(), patient.getFamily(), patient.getGiven(), getAge(patient.getDob()), patient.getSex(), "Borderline");
        when(diabetesReportService.getPatientInfo(patient)).thenReturn(patientInfo);
        String jsonContent = "Patient: " + patientInfo.getGiven() + " " + patientInfo.getFamilyName() + "(age " + patientInfo.getAge() + ") diabetes assessment is: " + patientInfo.getDiabetesLevel();

        mockMvc
                .perform(post("/assess/{patId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());

        verify(diabetesReportService).getPatientById(patient.getId());
    }

    @Test
    @DisplayName("Checking that the controller returns status code 200 and the patient's info when he/she is correctly fetched by his/her family name")
    public void shouldReturn200AndInfoWhenPatientIsFoundByFamilyName() throws Exception {
        when(diabetesReportService.getPatientByFamilyName(patient.getFamily())).thenReturn(patient);
        PatientInfo patientInfo = new PatientInfo(patient.getId(), patient.getFamily(), patient.getGiven(), getAge(patient.getDob()), patient.getSex(), "Borderline");
        when(diabetesReportService.getPatientInfo(patient)).thenReturn(patientInfo);
        String jsonContent = "Patient: " + patientInfo.getGiven() + " " + patientInfo.getFamilyName() + "(age " + patientInfo.getAge() + ") diabetes assessment is: " + patientInfo.getDiabetesLevel();

        mockMvc
                .perform(post("/assess")
                        .param("familyName", "TestBorderline")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());

        verify(diabetesReportService).getPatientByFamilyName(patient.getFamily());
    }
}
