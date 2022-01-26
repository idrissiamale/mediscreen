package com.mnote.service;

import com.mnote.dto.NoteDTO;
import com.mnote.exception.ResourceNotFoundException;
import com.mnote.mapper.NoteMapper;
import com.mnote.model.Note;
import com.mnote.repository.PatientHistoryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the PatientHistoryService interface.
 *
 * @see PatientHistoryService
 */
@Service
public class PatientHistoryServiceImpl implements PatientHistoryService {
    private static final Logger logger = LogManager.getLogger("PatientHistoryServiceImpl");
    private PatientHistoryRepository patientHistoryRepository;
    private NoteMapper noteMapper;

    @Autowired
    public PatientHistoryServiceImpl(PatientHistoryRepository patientHistoryRepository, NoteMapper noteMapper) {
        this.patientHistoryRepository = patientHistoryRepository;
        this.noteMapper = noteMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NoteDTO findById(String id) throws ResourceNotFoundException {
        logger.info("Patient's note was successfully fetched.");
        return noteMapper.modelToDto(patientHistoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("NoteNotFound", "The id provided is incorrect or does not exist: " + id, HttpStatus.NOT_FOUND)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<NoteDTO> findByPatId(Integer patId) throws ResourceNotFoundException {
        List<NoteDTO> notes = noteMapper.modelsToDto(patientHistoryRepository.findByPatId(patId));
        if (notes.isEmpty()) {
            throw new ResourceNotFoundException("PatientHistoryNotFound", "The id provided is incorrect or does not exist: " + patId, HttpStatus.NOT_FOUND);
        }
        logger.info("Patient's history was successfully fetched.");
        return notes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<NoteDTO> findAllNotes() {
        logger.info("All notes were successfully fetched.");
        return noteMapper.modelsToDto(patientHistoryRepository.findAll());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Note save(NoteDTO noteDTO) throws IllegalArgumentException {
        logger.info("Patient's note was saved successfully.");
        return patientHistoryRepository.save(noteMapper.dtoToModel(noteDTO));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NoteDTO update(String id, NoteDTO noteDTO) throws ResourceNotFoundException {
        return noteMapper.modelToDto(patientHistoryRepository.findById(id).map(noteToUpdate -> {
            noteToUpdate.setPatId(noteDTO.getPatId());
            noteToUpdate.setE(noteDTO.getNote());
            logger.info("Patient's history was updated successfully.");
            return patientHistoryRepository.save(noteToUpdate);
        }).orElseThrow(() -> new ResourceNotFoundException("HistoryNotFound", "The id provided is incorrect or does not exist: " + id, HttpStatus.NOT_FOUND)));
    }
}
