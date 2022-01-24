package com.clientui.controller;

import com.clientui.dto.NoteDTO;
import com.clientui.proxy.MicroservicePatientHistoryProxy;
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

/**
 * Controller class which returns Mediscreen's web pages such as patient's notes, 'add_note' and 'update_note' forms.
 *
 * @see com.clientui.proxy.MicroservicePatientHistoryProxy
 */
@Controller
@RequestMapping("/patHistory")
public class PatientHistoryController {
    private static final Logger logger = LogManager.getLogger("PatientHistoryController");

    private MicroservicePatientHistoryProxy microservicePatientHistoryProxy;

    @Autowired
    public PatientHistoryController(MicroservicePatientHistoryProxy microservicePatientHistoryProxy) {
        this.microservicePatientHistoryProxy = microservicePatientHistoryProxy;
    }

    /**
     * It displays the patientHistory page when a GET request to the following URL is made.
     *
     * @param patId - method parameter which is used as the path variable. It refers to the patient id.
     * @param model - it permits to add "notes" template to the model and to display all the patient's notes registered in Mediscreen.
     * @return the patient's history page.
     */
    @GetMapping("/notes/{patId}")
    public String home(@PathVariable("patId") Integer patId, Model model) {
        List<NoteDTO> notes = microservicePatientHistoryProxy.getPatientHistory(patId);
        model.addAttribute("notes", notes);
        return "patientHistory";
    }

    /**
     * It displays the 'add_note' form when a GET request to the following URL is made.
     *
     * @param noteDTO - DTO class. Must not be null.
     * @return the note form.
     */
    @GetMapping("/add")
    public String showAddNoteForm(NoteDTO noteDTO) {
        return "add_note";
    }

    /**
     * A method which saves patient's notes into database after the submission is completed and without errors.
     *
     * @param noteDTO - DTO class. Must not be null.
     * @param result  - permits to handle bind errors and to display it to the user - the practitioner - when there are errors on the form fields.
     * @return it redirects the practitioner to the patient's notes if the submission is completed and without errors. Otherwise the 'add_note' form is returned.
     */
    @PostMapping("/validate")
    public String addNote(@Valid NoteDTO noteDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add_note";
        }
        logger.info("Patient's note was saved successfully.");
        microservicePatientHistoryProxy.addNote(noteDTO);
        return "redirect:/patHistory/notes/" + noteDTO.getPatId();
    }

    /**
     * It displays the 'update_note' form when a GET request to the following URL is made.
     *
     * @param id    - it refers to note's id which is used as the path variable.
     * @param model - it permits to define NoteDTO as part of a Model and to display its data into form with the addAttribute method.
     * @return the update_note page.
     */
    @GetMapping("/update/{id}")
    public String showUpdateNoteForm(@PathVariable("id") String id, Model model) {
        NoteDTO noteDTO = microservicePatientHistoryProxy.getNote(id);
        logger.info("Patient was successfully fetched.");
        model.addAttribute("noteDTO", noteDTO);
        return "update_note";
    }

    /**
     * A method which updates patient's notes after the submission is completed and without errors.
     *
     * @param id      - it refers to note's id which is used as the path variable.
     * @param noteDTO - DTO class. Must not be null.
     * @param result  - permits to handle bind errors and to display it to the user - the practitioner - when there are errors on the form fields.
     * @return it redirects the patient's notes if the submission is completed and without errors. Otherwise the update_note form is returned.
     */
    @PostMapping("/update/{id}")
    public String updatePatient(@PathVariable("id") String id, @Valid NoteDTO noteDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "update_note";
        }
        logger.info("Patient data were updated successfully.");
        microservicePatientHistoryProxy.updatePatientHistory(id, noteDTO);
        return "redirect:/patHistory/notes/" + noteDTO.getPatId();
    }
}
