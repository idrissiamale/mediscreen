package com.mdiabetesReport.proxy;

import com.mdiabetesReport.dto.NoteDTO;
import com.mdiabetesReport.exception.ResourceNotFoundException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * An interface which permits to consume, with Feign client, the REST services exposed by Note microservice.
 */
@FeignClient(name = "microservice-note", url = "${mnote.url}")
public interface MicroservicePatientHistoryProxy {
    /**
     * Mapping a GET request in order to fetch the note with the given id.
     *
     * @param id, method parameter which is used as the path variable.
     * @return the note with the given id.
     * @throws ResourceNotFoundException if the note we want to fetch is not found.
     */
    @GetMapping(value = "patHistory/note/{id}")
    NoteDTO getNote(@PathVariable("id") String id) throws ResourceNotFoundException;

    /**
     * Mapping a GET request in order to fetch notes.
     *
     * @return all notes saved in Mediscreen.
     */
    @GetMapping("patHistory/notes")
    List<NoteDTO> getAllNotes();

    /**
     * Mapping a GET request in order to fetch patient's notes.
     *
     * @param patId, method parameter which is used as the path variable. It refers to the patient's id.
     * @return all patient's notes saved in Mediscreen.
     * @throws ResourceNotFoundException if the patient is not found.
     */
    @GetMapping(value = "/patHistory/notes/{patId}")
    List<NoteDTO> getPatientHistory(@PathVariable("patId") Integer patId) throws ResourceNotFoundException;
}
