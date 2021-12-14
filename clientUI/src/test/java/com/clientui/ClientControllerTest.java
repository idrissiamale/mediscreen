package com.clientui;

import com.clientui.controller.ClientController;
import com.clientui.dto.PatientRegistrationDTO;
import com.clientui.model.Patient;
import com.clientui.proxy.MicroservicePatientProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(controllers = ClientController.class)
public class ClientControllerTest {
    private MockMvc mockMvc;
    private Patient patient;
    private Patient patientUpdated;
    private PatientRegistrationDTO registration;
    private List<Patient> patients;


    @MockBean
    MicroservicePatientProxy microservicePatientProxy;


    @BeforeEach
    public void setup() {
        ClientController clientController = new ClientController(microservicePatientProxy);
        registration = new PatientRegistrationDTO("TestBordeline", "Test", "1945-06-24", "M", "2 High St", "200-333-4444");
        patient = new Patient(1, registration.getFamily(), registration.getGiven(), registration.getDob(), registration.getSex(), registration.getAddress(), registration.getPhone());
        patientUpdated = new Patient(1, "TestBordeline", "Lili", "1945-06-24", "M", "2 High St", "200-333-4444");
        Patient patient2 = new Patient(2, "TestInDanger", "Test", "1980-06-24", "F", "3 Club Road", "300-444-5555");
        patients = new ArrayList<>();
        patients.add(patient);
        patients.add(patient2);
        this.mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
    }

    @Test
    @DisplayName("Checking that the patient/list page is returned when the user makes a GET request to the /patient/list URL")
    public void shouldReturnPatientListView() throws Exception {
        when(microservicePatientProxy.getAllPatients()).thenReturn(patients);

        this.mockMvc.perform(get("/patient/list").contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("patients"))
                .andExpect(model().attribute("patients", patients))
                .andExpect(model().attribute("patients", hasSize(2)))
                .andExpect(model().attribute("patients", hasItem(
                        allOf(
                                hasProperty("family", is("TestBordeline")),
                                hasProperty("given", is("Test")),
                                hasProperty("dob", is("1945-06-24")),
                                hasProperty("sex", is("M")),
                                hasProperty("address", is("2 High St")),
                                hasProperty("phone", is("200-333-4444"))
                        )
                )))
                .andExpect(model().attribute("patients", hasItem(
                        allOf(
                                hasProperty("family", is("TestInDanger")),
                                hasProperty("given", is("Test")),
                                hasProperty("dob", is("1980-06-24")),
                                hasProperty("sex", is("F")),
                                hasProperty("address", is("3 Club Road")),
                                hasProperty("phone", is("300-444-5555"))
                        )
                )));

        verify(microservicePatientProxy).getAllPatients();
    }


    @Test
    @DisplayName("Checking that the registration form is returned when the user makes a GET request to the /patient/add URL")
    public void shouldReturnRegistrationView() throws Exception {
        this.mockMvc.perform(get("/patient/add").contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));

        verifyNoInteractions(microservicePatientProxy);
    }

    @Test
    @DisplayName("Checking that the registration page displays a success message when the patient has been successfully registered to our application")
    public void shouldReturnRegistrationSuccessMessageWhenPatientHasBeenSuccessfullyRegistered() throws Exception {
        when(microservicePatientProxy.addPatient(any(PatientRegistrationDTO.class))).thenReturn(patient);

        this.mockMvc.perform(post("/patient/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("family", "TestBordeline")
                .param("given", "Test")
                .param("dob", "1945-06-24")
                .param("sex", "M")
                .param("address", "2 High St")
                .param("phone", "200-333-4444")
                .sessionAttr("patientRegistrationDto", registration)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/patient/add?success"))
                .andExpect(redirectedUrl("/patient/add?success"))
                .andExpect(model().hasNoErrors());

        verify(microservicePatientProxy).addPatient(any(PatientRegistrationDTO.class));
        assertEquals("TestBordeline", registration.getFamily());
    }

    @Test
    @DisplayName("Checking that the registration form is returned with error message when there are errors on patient's personal informations")
    public void shouldReturnRegistrationFormWhenErrorsOnDateOfBirthAndGenderFields() throws Exception {
        String dateOfbirth = " ";
        String gender = null;

        this.mockMvc.perform(post("/patient/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("family", "TestBordeline")
                .param("given", "Test")
                .param("dob", dateOfbirth)
                .param("sex", gender)
                .param("address", "2 High St")
                .param("phone", "200-333-4444")
                .sessionAttr("patientRegistrationDto", registration)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("registration"))
                .andExpect(model().attributeHasFieldErrors("patientRegistrationDto", "dob"))
                .andExpect(model().attributeHasFieldErrors("patientRegistrationDto", "sex"))
                .andExpect(model().attribute("patientRegistrationDto", hasProperty("dob", is(dateOfbirth))))
                .andExpect(model().attribute("patientRegistrationDto", hasProperty("sex", is(gender))));

        verifyNoInteractions(microservicePatientProxy);
    }

    @Test
    @DisplayName("Checking that the 'update_patient' form  is returned when the user makes a GET request to the /patient/update/{id} URL")
    public void shouldReturnUpdatePatientFormView() throws Exception {
        when(microservicePatientProxy.getPatient(1)).thenReturn(patient);

        this.mockMvc.perform(get("/patient/update/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("update_patient"))
                .andExpect(model().attribute("patient", patient));

        verify(microservicePatientProxy).getPatient(1);
    }

    @Test
    @DisplayName("Checking that the user is redirected to the patient/list page when patient's data are correctly updated")
    public void shouldReturnPatientListPageWhenPatientDataAreCorrectlyUpdated() throws Exception {
        when(microservicePatientProxy.updatePatient(anyInt(), any(Patient.class))).thenReturn(patientUpdated);

        this.mockMvc.perform(post("/patient/update/{id}", 1).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("family", "TestBordeline")
                .param("given", "Lili")
                .param("dob", "1945-06-24")
                .param("sex", "M")
                .param("address", "2 High St")
                .param("phone", "200-333-4444")
                .sessionAttr("patient", patientUpdated)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/patient/list"))
                .andExpect(redirectedUrl("/patient/list"))
                .andExpect(model().hasNoErrors())
                .andDo(print());

        verify(microservicePatientProxy).updatePatient(anyInt(), any(Patient.class));
        assertEquals("Lili", patientUpdated.getGiven());
    }

    @Test
    @DisplayName("Checking that the 'update_patient' form is returned with error message when there are errors on the form fields")
    public void shouldReturnUpdatePatientFormWhenErrorsOnGivenField() throws Exception {
        String givenName = null;

        this.mockMvc.perform(post("/patient/update/{id}", 1).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("family", "TestBordeline")
                .param("given", givenName)
                .param("dob", "1945-06-24")
                .param("sex", "M")
                .param("address", "2 High St")
                .param("phone", "200-333-4444")
                .sessionAttr("patient", patientUpdated)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("update_patient"))
                .andExpect(model().attributeHasFieldErrors("patient", "given"))
                .andExpect(model().attribute("patient", hasProperty("given", is(givenName))))
                .andDo(print());

        verifyNoInteractions(microservicePatientProxy);
    }
}
