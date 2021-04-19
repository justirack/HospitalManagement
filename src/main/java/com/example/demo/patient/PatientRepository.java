package com.example.demo.patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient,Long> {

    @Query("select p from patient p where p.ssn = ?1")
    Optional<Patient>findPatientBySsn(Long ssn);
}
