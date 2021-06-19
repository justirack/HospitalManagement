/*
  Copyright 2021, Justin Rackley, All rights reserved.
*/
package com.hospital.manager.patient;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * This interface will allow access to the database.
 */
public interface PatientRepository extends JpaRepository<Patient,Long> {
    @Query("SELECT s FROM Patient s WHERE s.id = ?1")
    Optional<Patient>findPatientBySsn(final long id);
}