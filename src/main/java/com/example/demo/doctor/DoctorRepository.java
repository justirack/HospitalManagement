package com.example.demo.doctor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * This interface will allow access to the database.
 * @author - Justin Rackley
 */
public interface DoctorRepository extends JpaRepository<Doctor,Long> {

    @Query("SELECT s FROM Doctor s WHERE s.empId = ?1")
    Optional<Doctor> findDoctorByEmpId(Long ssn);
}
