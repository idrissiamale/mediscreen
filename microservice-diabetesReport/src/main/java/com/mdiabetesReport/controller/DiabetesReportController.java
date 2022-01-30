package com.mdiabetesReport.controller;

import com.mdiabetesReport.dto.PatientInfo;
import com.mdiabetesReport.exception.ResourceNotFoundException;
import com.mdiabetesReport.model.Patient;
import com.mdiabetesReport.service.DiabetesReportService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * DiabetesReportController's role is to generate the diabetes assessment for each patient of our application.
 *
 * @see com.mdiabetesReport.service.DiabetesReportService
 */
@RestController
@RequestMapping(value = "/assess")
public class DiabetesReportController {
    private static final Logger logger = LogManager.getLogger("DiabetesReportController");
    private DiabetesReportService diabetesReportService;

    @Autowired
    public DiabetesReportController(DiabetesReportService diabetesReportService) {
        this.diabetesReportService = diabetesReportService;
    }

    /**
     * Mapping a POST request in order to generate the diabetes assessment for the patient with the given id.
     *
     * @param patId, method parameter which should be bound to the web request body. It refers to the patient's id.
     * @return Patient's info including his/her diabetes level.
     * @throws IOException               if an I/O error occurs reading from the Keywords file.
     * @throws ResourceNotFoundException if the patient with the given id is not found.
     */
    @PostMapping(value = "/{patId}")
    public String generateDiabetesAssessmentByPatId(@PathVariable("patId") Integer patId) throws IOException, ResourceNotFoundException {
        Patient patient = diabetesReportService.getPatientById(patId);
        PatientInfo patientInfo = diabetesReportService.getPatientInfo(patient);
        logger.info("Diabetes assessment generated successfully for the patient with the following id : " + patientInfo.getId());
        return "Patient: " + patientInfo.getGiven() + " " + patientInfo.getFamilyName() + "(age " + patientInfo.getAge() + ") diabetes assessment is: " + patientInfo.getDiabetesLevel();
    }

    /**
     * Mapping a POST request in order to generate the diabetes assessment for the patient with the given family name.
     *
     * @param familyName, method parameter which should be bound to the web request body. It refers to the family name of the patient.
     * @return Patient's info including his/her diabetes level.
     * @throws IOException               if an I/O error occurs reading from the Keywords file.
     * @throws ResourceNotFoundException if the patient with the given family name is not found.
     */
    @PostMapping
    public String generateDiabetesAssessmentByFamilyName(@RequestParam("familyName") String familyName) throws IOException, ResourceNotFoundException {
        Patient patient = diabetesReportService.getPatientByFamilyName(familyName);
        PatientInfo patientInfo = diabetesReportService.getPatientInfo(patient);
        logger.info("Diabetes assessment generated successfully for the patient with the following name : " + patientInfo.getFamilyName());
        return "Patient: " + patientInfo.getGiven() + " " + patientInfo.getFamilyName() + "(age " + patientInfo.getAge() + ") diabetes assessment is: " + patientInfo.getDiabetesLevel();
    }
}
