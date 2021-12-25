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
    @GetMapping(value = "patHistory/note/{mongoDBid}")
    Note getNote(@PathVariable String mongoDBid) throws ResourceNotFoundException;

    @GetMapping(value = "/patHistory/notes/{patId}")
    List<Note> getPatientHistory(@PathVariable Integer patId) throws ResourceNotFoundException;

    @PutMapping(value = "/patHistory/update/{mongoDBid}")
    Note updatePatientHistory(@PathVariable String mongoDBid, @Valid @RequestBody Note note) throws ResourceNotFoundException;

    @PostMapping(value = "/patHistory/add")
    Note addNote(@Valid @RequestBody NoteDTO note) throws IllegalArgumentException;
}
