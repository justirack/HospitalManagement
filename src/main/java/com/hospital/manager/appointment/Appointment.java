/*
  Copyright 2021, Justin Rackley, All rights reserved.
*/
package com.hospital.manager.appointment;

import java.util.Date;

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
 * This class represents an appointment within the Hospital Management System.
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public final class Appointment {
    @Id
    @SequenceGenerator(name = "appointment_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appointment_sequence")
    private long id;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Doctor doctor;

    //how to make these non-nullable without adding @Column annotation?
    //maybe @NotNull, not sure though
    private Date date;

    private int room;
}