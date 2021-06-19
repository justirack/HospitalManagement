/*
  Copyright 2021, Justin Rackley, All rights reserved.
*/
package com.hospital.manager.drug;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class will represent a drug in the hospital management system.
 * A drug will have a formula, name and description.
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public final class Drug {
    @Id
    @SequenceGenerator(name = "drug_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "drug_sequence")
    private long id;

    @Column(nullable = false)
    private String formula;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;
}