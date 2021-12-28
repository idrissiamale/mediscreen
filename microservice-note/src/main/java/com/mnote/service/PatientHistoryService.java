package com.mnote.service;

import com.mnote.dto.NoteDTO;
import com.mnote.exception.ResourceNotFoundException;
import com.mnote.model.Note;

import java.util.List;

public interface PatientHistoryService {
    NoteDTO findById(String id) throws ResourceNotFoundException;

    List<NoteDTO> findByPatId(Integer patId) throws ResourceNotFoundException;

    Note save(NoteDTO noteDTO) throws IllegalArgumentException;

    NoteDTO update(String id, NoteDTO noteDTO) throws ResourceNotFoundException;
}
