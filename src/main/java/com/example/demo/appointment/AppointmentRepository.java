package com.example.demo.appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("SELECT s FROM Appointment s WHERE s.appId = ?1")
    Optional<Appointment> findAppointmentById(Long appId);
}
