/*
  Copyright 2021, Justin Rackley, All rights reserved.
*/
package com.hospital.manager.doctor;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * This interface will allow access to the database.
 */
public interface DoctorRepository extends JpaRepository<Doctor,Long> {
    @Query("SELECT s FROM Doctor s WHERE s.id = ?1")
    Optional<Doctor> findDoctorByEmpId(final long id);
}