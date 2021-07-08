/*
  Copyright 2021, Justin Rackley, All rights reserved.
*/
package com.hospital.manager.doctor;

import com.hospital.manager.appointment.Appointment;
import com.hospital.manager.patient.Patient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents doctors in the hospital management system.
 * Each doctor will have an id, first name, last name and phone.
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public final class Doctor {

    /**
     * The unique database identifier for this Doctor. This cannot be null,
     * but it can be set to 0L if this object has never been persisted to the database
     * yet.
     */
    @Id
    @SequenceGenerator(name = "doctor_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "doctor_sequence")
    private long id;

    /**
     * The doctors first name. This cannot be null.
     */
    @NonNull
    private String firstName;

    /**
     * The doctors last name. This cannot be null.
     */
    @NonNull
    private String lastName;

    /**
     * The doctors phone number. This cannot be null.
     */
    @NonNull
    private String phone;

    /**
     * A list of all of a doctors appointments. This cannot be null.
     */
    @OneToMany
    private List<Appointment> appointments = new ArrayList<>();

    /**
     * A list of all of a doctors patients. This cannot be null.
     */
    @OneToMany
    private List<Patient> patients = new ArrayList<>();
}