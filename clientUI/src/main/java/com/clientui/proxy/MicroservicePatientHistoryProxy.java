package com.clientui.proxy;

import com.clientui.exception.ResourceNotFoundException;
import com.clientui.model.Note;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "microservice-note", url = "${mnote.url}")
public interface MicroservicePatientHistoryProxy {
    @GetMapping(value = "/{id}")
    List<Note> getPatientHistory(@PathVariable Integer id) throws ResourceNotFoundException;
}
