/*
  Copyright 2021, Justin Rackley, All rights reserved.
*/
package com.hospital.manager.drug;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * This interface will allow access to the database.
 */
public interface DrugRepository extends JpaRepository<Drug, Long> {
    @Query("SELECT s FROM Drug s WHERE s.formula = ?1")
    Optional<Drug> findDrugByFormula(final long id);
}