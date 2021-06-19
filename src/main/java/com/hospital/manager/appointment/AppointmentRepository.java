/*
  Copyright 2021, Justin Rackley, All rights reserved.
*/
package com.hospital.manager.appointment;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * This interface will allow access to the database.
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("SELECT s FROM Appointment s WHERE s.id = ?1")
    Optional<Appointment> findAppointmentById(final long id);

    @Query("SELECT s FROM Appointment s WHERE s.doctor.id = ?1")
    List<Appointment> findDoctorsAppointments(final long id);
}