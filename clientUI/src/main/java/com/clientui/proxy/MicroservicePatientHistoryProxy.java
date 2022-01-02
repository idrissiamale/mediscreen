package com.clientui.proxy;

import com.clientui.dto.NoteDTO;
import com.clientui.exception.ResourceNotFoundException;
import com.clientui.model.Note;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "microservice-note", url = "${mnote.url}")
public interface MicroservicePatientHistoryProxy {
    @GetMapping(value = "patHistory/note/{id}")
    NoteDTO getNote(@PathVariable("id") String id) throws ResourceNotFoundException;

    @GetMapping(value = "/patHistory/notes/{patId}")
    List<NoteDTO> getPatientHistory(@PathVariable("patId") Integer patId) throws ResourceNotFoundException;

    @PutMapping(value = "/patHistory/update/{id}")
    NoteDTO updatePatientHistory(@PathVariable("id") String id, @Valid @RequestBody NoteDTO noteDTO) throws ResourceNotFoundException;

    @PostMapping(value = "/patHistory/add")
    Note addNote(@Valid @RequestBody NoteDTO noteDTO) throws IllegalArgumentException;
}
