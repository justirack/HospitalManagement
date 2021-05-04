package com.example.demo.drug;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * This interface will allow access to the database.
 * @author - Justin Rackley
 */
public interface DrugRepository extends JpaRepository<Drug, Long> {
    @Query("SELECT s FROM Drug s WHERE s.formula = ?1")
    Optional<Drug> findDrugByFormula(Long id);
}
