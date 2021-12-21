package com.mnote.service;

import com.mnote.dto.NoteDTO;
import com.mnote.exception.ResourceNotFoundException;
import com.mnote.model.Note;
import com.mnote.repository.PatientHistoryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientHistoryServiceImpl implements PatientHistoryService {
    private static final Logger logger = LogManager.getLogger("PatientHistoryServiceImpl");
    private PatientHistoryRepository patientHistoryRepository;

    @Autowired
    public PatientHistoryServiceImpl(PatientHistoryRepository patientHistoryRepository) {
        this.patientHistoryRepository = patientHistoryRepository;
    }


    @Override
    public List<Note> findByPatId(Integer patId) throws ResourceNotFoundException {
        if (patId == null) {
            throw new ResourceNotFoundException("PatientHistoryNotFound", "The id provided is incorrect or does not exist: " + patId, HttpStatus.NOT_FOUND);
        }
        logger.info("Patient's history was successfully fetched.");
        return patientHistoryRepository.findByPatId(patId);
    }

    @Override
    public List<Note> findAll() {
        logger.info("Patients' histories were successfully fetched.");
        return patientHistoryRepository.findAll();
    }

    @Override
    public Note save(NoteDTO note) throws IllegalArgumentException {
        Note noteToSave = new Note();
        noteToSave.setPatId(note.getPatId());
        noteToSave.setNote(note.getE());
        logger.info("Patient's note was saved successfully.");
        return patientHistoryRepository.save(noteToSave);
    }

    @Override
    public Note update(String id, Note note) throws ResourceNotFoundException {
        return patientHistoryRepository.findById(id).map(noteToUpdate -> {
            noteToUpdate.setPatId(note.getPatId());
            noteToUpdate.setNote(note.getNote());
            logger.info("Patient's history was updated successfully.");
            return patientHistoryRepository.save(noteToUpdate);
        }).orElseThrow(() -> new ResourceNotFoundException("HistoryNotFound", "The id provided is incorrect or does not exist: " + id, HttpStatus.NOT_FOUND));
    }
}
