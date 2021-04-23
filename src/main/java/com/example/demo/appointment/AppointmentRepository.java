package com.example.demo.appointment;

import com.example.demo.doctor.Doctor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AppointmentRepository {
    @Query("SELECT s FROM Appointment s WHERE s. = ?1")
    Optional<Doctor> findDoctorByEmpId(Long ssn);
}
