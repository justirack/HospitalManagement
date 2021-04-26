package com.example.demo.appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * This interface will allow access to the database.
 * @author - Justin Rackley
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("SELECT s FROM Appointment s WHERE s.appId = ?1")
    Optional<Appointment> findAppointmentById(Long appId);

    @Query("SELECT s FROM Appointment s WHERE s.doctorEmpId = ?1")
    List<Appointment> findDoctorsAppointments(Long empId);
}
