package com.example.demo.prescription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * This interface will allow access to the database.
 * @author - Justin Rackley
 */
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    @Query("SELECT s FROM Prescription s WHERE s.presId = ?1")
    Optional<Prescription> findPrescriptionByPresId(Long ssn);
}
