/*
  Copyright 2021, Justin Rackley, All rights reserved.
*/
package com.hospital.manager.drug;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * <p>
 *     This interface will allow access to the database.
 * </p>
 */
public interface DrugRepository extends JpaRepository<Drug, Long> {
    @Query("SELECT s FROM Drug s WHERE s.id = ?1")
    Optional<Drug> findDrugById(final Long id);

    @Query("SELECT s FROM Drug s WHERE s.name = ?1")
    Optional<Drug> findDrugByName(final String name);
}