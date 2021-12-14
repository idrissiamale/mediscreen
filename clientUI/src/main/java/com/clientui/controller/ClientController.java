package com.clientui.controller;

import com.clientui.dto.PatientRegistrationDTO;
import com.clientui.model.Patient;
import com.clientui.proxy.MicroservicePatientProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * Controller class which returns Mediscreen's web pages such as patients list, registration and update forms.
 *
 * @see com.clientui.proxy.MicroservicePatientProxy
 */
@Controller
@RequestMapping("/patient")
public class ClientController {
    private static final Logger logger = LogManager.getLogger("ClientController");

    private MicroservicePatientProxy microservicePatientProxy;

    @Autowired
    public ClientController(MicroservicePatientProxy microservicePatientProxy) {
        this.microservicePatientProxy = microservicePatientProxy;
    }

    /**
     * It displays the patient/list page when a GET request to the following URL is made.
     *
     * @param model - it permits to add "patients" template to the model and to display all the patients registered in Mediscreen.
     * @return the patients page.
     */
    @GetMapping("/list")
    public String home(Model model) {
        model.addAttribute("patients", microservicePatientProxy.getAllPatients());
        return "patients";
    }

    /**
     * It displays the registration form when a GET request to the following URL is made.
     *
     * @param registration - Patient entity. Must not be null.
     * @return the registration page.
     */
    @GetMapping("/add")
    public String showRegistrationForm(PatientRegistrationDTO registration) {
        return "registration";
    }

    /**
     * A method which saves patient's data into database after the submission is completed and without errors.
     *
     * @param registration - Patient entity. Must not be null.
     * @param result       - permits to handle bind errors and to display it to the user when there are errors on the form fields.
     * @return it returns a success message to the user if the submission is completed and without errors. Otherwise the registration form is returned.
     */
    @PostMapping("/validate")
    public String registerPatient(@Valid PatientRegistrationDTO registration, BindingResult result) {
        if (result.hasErrors()) {
            return "registration";
        }
        logger.info("Patient saved successfully.");
        microservicePatientProxy.addPatient(registration);
        return "redirect:/patient/add?success";
    }

    /**
     * It displays the update form when a GET request to the following URL is made.
     *
     * @param id    - it refers to patient's id which is used as the path variable.
     * @param model - it permits to define Patient entity as part of a Model and to display its data into form with the addAttribute method.
     * @return the update_patient page.
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("Patient was successfully fetched.");
        model.addAttribute("patient", microservicePatientProxy.getPatient(id));
        return "update_patient";
    }

    /**
     * A method which updates patient's data after the submission is completed and without errors.
     *
     * @param id      - it refers to patient's id which is used as the path variable.
     * @param patient - Patient entity. Must not be null.
     * @param result  - permits to handle bind errors and to display it to the user when there are errors on the form fields.
     * @param model   - it permits to add "patients" to the model and to display all the patients registered in Mediscreen when the user is redirected to patients page.
     * @return it redirects the user to the patients page if the submission is completed and without errors. Otherwise the update_patient form is returned.
     */
    @PostMapping("/update/{id}")
    public String updatePatient(@PathVariable("id") Integer id, @Valid Patient patient, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "update_patient";
        }
        logger.info("Patient data were updated successfully.");
        microservicePatientProxy.updatePatient(id, patient);
        model.addAttribute("patients", microservicePatientProxy.getAllPatients());
        return "redirect:/patient/list";
    }
}
