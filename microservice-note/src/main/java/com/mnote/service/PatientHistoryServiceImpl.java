package com.mnote.service;

import com.mnote.dto.PatientHistoryDTO;
import com.mnote.model.PatientHistory;
import com.mnote.repository.PatientHistoryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientHistoryServiceImpl implements PatientHistoryService {
    private static final Logger logger = LogManager.getLogger("PatientHistoryServiceImpl");
    private PatientHistoryRepository patientHistoryRepository;

    @Autowired
    public PatientHistoryServiceImpl(PatientHistoryRepository patientHistoryRepository) {
        this.patientHistoryRepository = patientHistoryRepository;
    }


    @Override
    public PatientHistory save(PatientHistoryDTO history) throws IllegalArgumentException {
        PatientHistory patientHistory = new PatientHistory();
        patientHistory.setPatId(history.getPatId());
        patientHistory.setNote(history.getE());
        logger.info("Patient's history was saved successfully.");
        return patientHistoryRepository.save(patientHistory);
    }
}
