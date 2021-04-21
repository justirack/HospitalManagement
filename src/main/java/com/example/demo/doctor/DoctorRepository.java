/**
 * This class will connect with the database
 * @author - Justin Rackley
 */

package com.example.demo.doctor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {

    @Query("SELECT s FROM Doctor s WHERE s.empId = ?1")
    Optional<Doctor> findPatientBySsn(Long ssn);
}
