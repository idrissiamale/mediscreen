package com.mnote.service;

import com.mnote.dto.PatientHistoryDTO;
import com.mnote.model.PatientHistory;

public interface PatientHistoryService {
    PatientHistory save(PatientHistoryDTO history) throws IllegalArgumentException;
}
