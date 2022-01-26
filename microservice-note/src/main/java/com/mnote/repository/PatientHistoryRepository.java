package com.mnote.repository;

import com.mnote.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * An interface which provides, by extending MongoRepository, generic CRUD operations to implement on PatientHistory repository.
 *
 * @see MongoRepository
 */
@Repository
public interface PatientHistoryRepository extends MongoRepository<Note, String> {
    /**
     * A method which retrieves a list of Patient's notes by his/her id.
     *
     * @param patId - must not be null. It refers to the Patient's id.
     * @return the notes of the patient with the given id.
     * @throws IllegalArgumentException if given id is null.
     */
    List<Note> findByPatId(Integer patId);
}
