package com.mdiabetesReport.service;

import com.mdiabetesReport.dto.PatientInfo;
import com.mdiabetesReport.exception.ResourceNotFoundException;
import com.mdiabetesReport.util.KeywordsFileReader;
import com.mdiabetesReport.model.Patient;
import com.mdiabetesReport.proxy.MicroservicePatientHistoryProxy;
import com.mdiabetesReport.proxy.MicroservicePatientProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.io.IOException;

import static com.mdiabetesReport.helper.HelperClass.getAge;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DiabetesReportServiceImplTest {
    private DiabetesReportServiceImpl diabetesReportServiceImpl;
    private Patient patient;

    @Mock
    private MicroservicePatientProxy microservicePatientProxy;
    @Mock
    private MicroservicePatientHistoryProxy microservicePatientHistoryProxy;
    @Mock
    private KeywordsFileReader keywordsFileReader;

    @BeforeEach
    void setUp() {
        String filePath = "/Users/idrissi/Workspace/MediscreenProject/microservice-diabetesReport/src/main/resources/keywords.txt";
        keywordsFileReader = new KeywordsFileReader(filePath);
        diabetesReportServiceImpl = new DiabetesReportServiceImpl(keywordsFileReader, microservicePatientProxy, microservicePatientHistoryProxy);
        patient = new Patient(1, "TestBorderline", "Test", "1945-06-24", "M", "2 High St", "200-333-4444");
    }

    @Test
    @DisplayName("Checking that the PatientInfo is correctly fetched by comparing expected and the actual patient's diabetes level")
    public void shouldReturnPatientInfoWithDiabetesLevel() throws IOException {
        PatientInfo patientInfo = new PatientInfo(patient.getId(), patient.getFamily(), patient.getGiven(), getAge(patient.getDob()), patient.getSex(), "Borderline");

        diabetesReportServiceImpl.getPatientInfo(patient);

        assertEquals("Borderline", patientInfo.getDiabetesLevel());
    }

    @Test
    @DisplayName("Checking that the patient is correctly fetched by his/her id")
    public void shouldFindPatientById() {
        when(microservicePatientProxy.getPatientById(1)).thenReturn(patient);

        Patient patientToFetch = diabetesReportServiceImpl.getPatientById(1);

        verify(microservicePatientProxy).getPatientById(1);
        assertEquals(patient.getId(), patientToFetch.getId());
    }

    @Test
    @DisplayName("Checking that ResourceNotFoundException is thrown when patient's id does not exist")
    public void shouldThrowExceptionWhenPatientNotFoundById() {
        doThrow(new ResourceNotFoundException("PatientNotFound", "The id provided is incorrect or does not exist: ", HttpStatus.NOT_FOUND)).when(microservicePatientProxy).getPatientById(7);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> diabetesReportServiceImpl.getPatientById(7));

        verify(microservicePatientProxy).getPatientById(7);
        assertEquals(exception.getMessage(), "The id provided is incorrect or does not exist: ");
    }

    @Test
    @DisplayName("Checking that the patient is correctly fetched by his/her family name")
    public void shouldFindPatientByFamilyName() {
        when(microservicePatientProxy.getPatientByFamilyName("TestBorderline")).thenReturn(patient);

        Patient patientToFetch = diabetesReportServiceImpl.getPatientByFamilyName("TestBorderline");

        verify(microservicePatientProxy).getPatientByFamilyName("TestBorderline");
        assertEquals(patient.getFamily(), patientToFetch.getFamily());
    }

    @Test
    @DisplayName("Checking that ResourceNotFoundException is thrown when patient's family name does not exist")
    public void shouldThrowExceptionWhenPatientNotFoundByFamilyName() {
        doThrow(new ResourceNotFoundException("PatientNotFound", "The name provided is incorrect or does not exist: ", HttpStatus.NOT_FOUND)).when(microservicePatientProxy).getPatientByFamilyName("amale");

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> diabetesReportServiceImpl.getPatientByFamilyName("amale"));

        verify(microservicePatientProxy).getPatientByFamilyName("amale");
        assertEquals(exception.getMessage(), "The name provided is incorrect or does not exist: ");
    }
}
