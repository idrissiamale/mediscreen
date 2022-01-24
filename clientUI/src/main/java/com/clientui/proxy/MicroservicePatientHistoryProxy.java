package com.clientui.proxy;

import com.clientui.dto.NoteDTO;
import com.clientui.exception.ResourceNotFoundException;
import com.clientui.model.Note;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
     * Mapping a GET request in order to fetch patient's notes.
     *
     * @param patId, method parameter which is used as the path variable. It refers to the patient's id.
     * @return all patient's notes saved in Mediscreen.
     * @throws ResourceNotFoundException if the patient is not found.
     */
    @GetMapping(value = "/patHistory/notes/{patId}")
    List<NoteDTO> getPatientHistory(@PathVariable("patId") Integer patId) throws ResourceNotFoundException;

    /**
     * Mapping a PUT request in order to update a patient's note.
     *
     * @param id,      method parameter which is used as the path variable. It refers to the note's id.
     * @param noteDTO, method parameter which should be bound to the web request body.
     * @return the updated patient's note.
     * @throws ResourceNotFoundException if the note with the given id is not found.
     */
    @PutMapping(value = "/patHistory/update/{id}")
    NoteDTO updatePatientHistory(@PathVariable("id") String id, @Valid @RequestBody NoteDTO noteDTO) throws ResourceNotFoundException;

    /**
     * Mapping a POST request in order to add a new note.
     *
     * @param noteDTO, method parameter which should be bound to the web request body.
     * @return the newly saved note.
     * @throws IllegalArgumentException if the NoteDTO's fields are empty or null.
     */
    @PostMapping(value = "/patHistory/add")
    Note addNote(@Valid @RequestBody NoteDTO noteDTO) throws IllegalArgumentException;
}
