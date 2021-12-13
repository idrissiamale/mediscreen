package com.mpatientHistory.repository;

import com.mpatientHistory.model.PatientHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PatientHistoryRepository extends MongoRepository<PatientHistory, Integer> {
}
