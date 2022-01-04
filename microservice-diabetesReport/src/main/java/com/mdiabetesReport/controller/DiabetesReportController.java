package com.mdiabetesReport.controller;

import com.mdiabetesReport.dto.NoteDTO;
import com.mdiabetesReport.exception.ResourceNotFoundException;
import com.mdiabetesReport.model.Patient;
import com.mdiabetesReport.service.DiabetesReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/assess")
public class DiabetesReportController {
    private DiabetesReportService diabetesReportService;

    @Autowired
    public DiabetesReportController(DiabetesReportService diabetesReportService) {
        this.diabetesReportService = diabetesReportService;
    }

    @GetMapping(value = "/patient/list")
    List<Patient> getAllPatients() {
        return diabetesReportService.getAllPatients();
    }

    @GetMapping(value = "/patHistory/notes/{patId}")
    List<NoteDTO> getPatientHistory(@PathVariable("patId") Integer patId) throws ResourceNotFoundException {
        return diabetesReportService.getPatientNotes(patId);
    }
}
