/*
  Copyright 2021, Justin Rackley, All rights reserved.
*/
package com.hospital.manager.prescription;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * This interface will allow access to the database.
 * @author - Justin Rackley
 */
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    @Query("SELECT s FROM Prescription s WHERE s.id = ?1")
    Optional<Prescription> findPrescriptionByPresId(final long id);
}
