package com.mpatient.repository;

import com.mpatient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * An interface which provides, by extending JpaRepository, generic CRUD operations to implement on Patient repository.
 *
 * @see JpaRepository
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
}
