/*
  Copyright 2021, Justin Rackley, All rights reserved.
*/
package com.hospital.manager.doctor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * This interface will allow access to the database.
 */
public interface DoctorRepository extends JpaRepository<Doctor,Long> {
    @Query("SELECT s FROM Doctor s WHERE s.id = ?1")
    Optional<Doctor> findDoctorByEmpId(final long id);

    @Query("SELECT s FROM Doctor s WHERE s.phone = ?1")
    Optional<Doctor> findDoctorByPhone(final String phone);
}