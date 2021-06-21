/*
  Copyright 2021, Justin Rackley, All rights reserved.
*/
package com.hospital.manager.prescription;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.hospital.manager.doctor.Doctor;
import com.hospital.manager.patient.Patient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class will represent a prescription in the hospital management system.
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public final class Prescription {
    @Id
    @SequenceGenerator(name = "prescription_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prescription_sequence")
    private long id;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Doctor doctor;

    @Column(nullable = false)
    private long amount;
}