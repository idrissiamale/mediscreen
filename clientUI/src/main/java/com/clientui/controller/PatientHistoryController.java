package com.clientui.controller;

import com.clientui.dto.NoteDTO;
import com.clientui.dto.PatientRegistrationDTO;
import com.clientui.model.Note;
import com.clientui.model.Patient;
import com.clientui.proxy.MicroservicePatientHistoryProxy;
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
import java.util.List;

@Controller
@RequestMapping("/patHistory")
public class PatientHistoryController {
    private static final Logger logger = LogManager.getLogger("PatientHistoryController");

    private MicroservicePatientHistoryProxy microservicePatientHistoryProxy;

    @Autowired
    public PatientHistoryController(MicroservicePatientHistoryProxy microservicePatientHistoryProxy) {
        this.microservicePatientHistoryProxy = microservicePatientHistoryProxy;
    }

    @GetMapping("/notes/{patId}")
    public String home(@PathVariable("patId") Integer patId, Model model) {
        List<NoteDTO> notes = microservicePatientHistoryProxy.getPatientHistory(patId);
        model.addAttribute("notes", notes);
        return "patientHistory";
    }

    @GetMapping("/add")
    public String showAddNoteForm(NoteDTO noteDTO) {
        return "add_note";
    }

    @PostMapping("/validate")
    public String addNote(@Valid NoteDTO noteDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add_note";
        }
        logger.info("Patient's note was saved successfully.");
        microservicePatientHistoryProxy.addNote(noteDTO);
        return "redirect:/patHistory/notes/" + noteDTO.getPatId();
    }

    @GetMapping("/update/{id}")
    public String showUpdateNoteForm(@PathVariable("id") String id, Model model) {
        NoteDTO noteDTO = microservicePatientHistoryProxy.getNote(id);
        logger.info("Patient was successfully fetched.");
        model.addAttribute("noteDTO", noteDTO);
        return "update_note";
    }

    @PostMapping("/update/{id}")
    public String updatePatient(@PathVariable("id") String id, @Valid NoteDTO noteDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "update_note";
        }
        logger.info("Patient data were updated successfully.");
        microservicePatientHistoryProxy.updatePatientHistory(id, noteDTO);
        return "redirect:/patHistory/notes/" + noteDTO.getPatId();
    }
}
