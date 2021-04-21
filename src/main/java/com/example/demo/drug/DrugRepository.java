/**
 * This class will connect with the database
 * @author - Justin Rackley
 */

package com.example.demo.drug;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DrugRepository extends JpaRepository<Drug, String> {
    @Query("SELECT s FROM Drug s WHERE s.formula = ?1")
    Optional<Drug> findPatientBySsn(Long ssn);
}
