package com.mpatientHistory.service;

import com.mpatientHistory.model.PatientHistory;
import com.mpatientHistory.repository.PatientHistoryRepository;
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

    @Autowired
    public PatientHistoryServiceImpl(PatientHistoryRepository patientHistoryRepository) {
        this.patientHistoryRepository = patientHistoryRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PatientHistory findById(Integer id) throws ResourceNotFoundException {
        logger.info("Patient's history was successfully fetched.");
        return patientHistoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("HistoryNotFound", "The id provided is incorrect or does not exist: " + id, HttpStatus.NOT_FOUND));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PatientHistory> findAll() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PatientHistory save(PatientHistory history) throws IllegalArgumentException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PatientHistory update(Integer id, PatientHistory history) throws IllegalArgumentException {
        return null;
    }
}
