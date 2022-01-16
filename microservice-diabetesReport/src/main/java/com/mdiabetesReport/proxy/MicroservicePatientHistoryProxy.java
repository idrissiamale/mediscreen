package com.mdiabetesReport.proxy;

import com.mdiabetesReport.dto.NoteDTO;
import com.mdiabetesReport.exception.ResourceNotFoundException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservice-note", url = "${mnote.url}")
public interface MicroservicePatientHistoryProxy {
    @GetMapping(value = "patHistory/note/{id}")
    NoteDTO getNote(@PathVariable("id") String id) throws ResourceNotFoundException;

    @GetMapping("patHistory/notes")
    List<NoteDTO> getAllNotes();

    @GetMapping(value = "/patHistory/notes/{patId}")
    List<NoteDTO> getPatientHistory(@PathVariable("patId") Integer patId) throws ResourceNotFoundException;
}
