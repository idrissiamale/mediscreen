package com.mnote.service;

import com.mnote.dto.PatientHistoryDTO;
import com.mnote.exception.ResourceNotFoundException;
import com.mnote.model.PatientHistory;
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
    public PatientHistory findById(Integer id) throws ResourceNotFoundException {
        logger.info("Patient's history was successfully fetched.");
        return patientHistoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("PatientHistoryNotFound", "The id provided is incorrect or does not exist: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public List<PatientHistory> findAll() {
        logger.info("Patients' histories were successfully fetched.");
        return patientHistoryRepository.findAll();
    }

    @Override
    public PatientHistory save(PatientHistoryDTO history) throws IllegalArgumentException {
        PatientHistory patientHistory = new PatientHistory();
        patientHistory.setPatId(history.getPatId());
        patientHistory.setNote(history.getE());
        logger.info("Patient's history was saved successfully.");
        return patientHistoryRepository.save(patientHistory);
    }

    @Override
    public PatientHistory update(Integer id, PatientHistory history) throws ResourceNotFoundException {
        return patientHistoryRepository.findById(id).map(historyToUpdate -> {
            historyToUpdate.setPatId(history.getPatId());
            historyToUpdate.setNote(history.getNote());
            logger.info("Patient's history was updated successfully.");
            return patientHistoryRepository.save(historyToUpdate);
        }).orElseThrow(() -> new ResourceNotFoundException("HistoryNotFound", "The id provided is incorrect or does not exist: " + id, HttpStatus.NOT_FOUND));
    }
}
