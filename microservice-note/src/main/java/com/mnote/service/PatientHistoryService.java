package com.mnote.service;

import com.mnote.dto.NoteDTO;
import com.mnote.exception.ResourceNotFoundException;
import com.mnote.model.Note;

import java.util.List;

public interface PatientHistoryService {
    List<Note> findByPatId(Integer patId) throws ResourceNotFoundException;

    List<Note> findAll();

    Note save(NoteDTO note) throws IllegalArgumentException;

    Note update(String id, Note note) throws ResourceNotFoundException;
}
