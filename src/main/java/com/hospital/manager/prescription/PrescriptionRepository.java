/*
  Copyright 2021, Justin Rackley, All rights reserved.
*/
package com.hospital.manager.prescription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * <p>
 *     This interface will allow access to the database.
 * </p>
 */
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    @Query("SELECT s FROM Prescription s WHERE s.id = ?1")
    Optional<Prescription> findPrescriptionByPresId(final long id);

    @Query("SELECT s FROM Prescription s WHERE s.name = ?1")
    Optional<Prescription> findPrescriptionByName(final String name);
}
