package com.mnote.service;

import com.mnote.dto.PatientHistoryDTO;
import com.mnote.exception.ResourceNotFoundException;
import com.mnote.model.PatientHistory;

import java.util.List;

public interface PatientHistoryService {
    PatientHistory findById(Integer id) throws ResourceNotFoundException;

    List<PatientHistory> findAll();

    PatientHistory save(PatientHistoryDTO history) throws IllegalArgumentException;

    PatientHistory update(Integer id, PatientHistory history) throws ResourceNotFoundException;
}
