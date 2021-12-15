package com.mnote.repository;

import com.mnote.model.PatientHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientHistoryRepository extends MongoRepository<PatientHistory, Integer> {
}
