package com.mdiabetesReport.controller;

import com.mdiabetesReport.dto.PatientInfo;
import com.mdiabetesReport.exception.ResourceNotFoundException;
import com.mdiabetesReport.model.Patient;
import com.mdiabetesReport.service.DiabetesReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/assess")
public class DiabetesReportController {
    private DiabetesReportService diabetesReportService;

    @Autowired
    public DiabetesReportController(DiabetesReportService diabetesReportService) {
        this.diabetesReportService = diabetesReportService;
    }

    @PostMapping(value = "/{patId}")
    public String getPatientInfoByPatId(@PathVariable("patId") Integer patId) throws IOException, ResourceNotFoundException {
        Patient patient = diabetesReportService.getPatientById(patId);
        PatientInfo patientInfo = diabetesReportService.getPatientInfo(patient);
        return "Patient: " + patientInfo.getGiven() + " " + patientInfo.getFamilyName() + "(age " + patientInfo.getAge() + ") diabetes assessment is: " + patientInfo.getDiabetesLevel();
    }

    @PostMapping
    public String getPatientInfoByFamilyName(@RequestParam("familyName") String familyName) throws IOException, ResourceNotFoundException {
        Patient patient = diabetesReportService.getPatientByFamilyName(familyName);
        PatientInfo patientInfo = diabetesReportService.getPatientInfo(patient);
        return "Patient: " + patientInfo.getGiven() + " " + patientInfo.getFamilyName() + "(age " + patientInfo.getAge() + ") diabetes assessment is: " + patientInfo.getDiabetesLevel();
    }
}
