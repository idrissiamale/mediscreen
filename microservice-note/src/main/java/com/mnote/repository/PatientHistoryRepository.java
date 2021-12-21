package com.mnote.repository;

import com.mnote.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientHistoryRepository extends MongoRepository<Note, String> {
    List<Note> findByPatId(Integer patId);
}
