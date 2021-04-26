package com.example.demo.patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * This interface will allow access to the database.
 * @author - Justin Rackley
 */
public interface PatientRepository extends JpaRepository<Patient,Long> {
    @Query("SELECT s FROM Patient s WHERE s.ssn = ?1")
    Optional<Patient>findPatientBySsn(Long ssn);
}
