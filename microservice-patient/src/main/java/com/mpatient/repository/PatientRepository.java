package com.mpatient.repository;

import com.mpatient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * An interface which provides, by extending JpaRepository, generic CRUD operations to implement on Patient repository.
 *
 * @see JpaRepository
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    /**
     * A method which retrieves Patient entity by its family name.
     *
     * @param family - must not be null.
     * @return the Patient entity with the given name or Optional#empty() if none found.
     * @throws IllegalArgumentException if given name is null.
     */
    Optional<Patient> findByFamily(String family);
}
