/*
  Copyright 2021, Justin Rackley, All rights reserved.
*/
package com.hospital.manager.doctor;

import com.hospital.manager.appointment.Appointment;
import com.hospital.manager.patient.Patient;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public final class Doctor {
    @Id
    @SequenceGenerator(name = "doctor_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "doctor_sequence")
    private long id;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String phone;

    @OneToMany
    private List<Appointment> appointments = new ArrayList<>();

    @OneToMany
    private List<Patient> patients = new ArrayList<>();
}