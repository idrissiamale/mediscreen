package com.mdiabetesReport.controller;

import com.mdiabetesReport.dto.NoteDTO;
import com.mdiabetesReport.exception.ResourceNotFoundException;
import com.mdiabetesReport.model.Patient;
import com.mdiabetesReport.service.DiabetesReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/assess")
public class DiabetesReportController {
    private DiabetesReportService diabetesReportService;

    private ResourceLoader resourceLoader;

    @Autowired
    public DiabetesReportController(DiabetesReportService diabetesReportService, ResourceLoader resourceLoader) {
        this.diabetesReportService = diabetesReportService;
        this.resourceLoader = resourceLoader;
    }

    @GetMapping(value = "/patient/list")
    List<Patient> getAllPatients() {
        return diabetesReportService.getAllPatients();
    }

    @GetMapping(value = "/patHistory/notes/{patId}")
    List<NoteDTO> getPatientHistory(@PathVariable("patId") Integer patId) throws ResourceNotFoundException {
        return diabetesReportService.getPatientNotes(patId);
    }

    @GetMapping(value = "/file")
    public File getFile() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:keywords.txt");
        System.out.println(resource);
        return resource.getFile();
    }
}
