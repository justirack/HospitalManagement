/*
  Copyright 2021, Justin Rackley, All rights reserved.
*/
package com.hospital.manager.patient;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.hospital.manager.appointment.Appointment;
import com.hospital.manager.doctor.Doctor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class represents patients in the hospital management system.
 * Each patient will have a ssn, family doctor, first name, last name, phone and address.
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public final class Patient {
    @Id
    @SequenceGenerator(name = "patient_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_sequence")
    private long id;

    private String firstName;
    private String lastName;
    private String phone;
    private String address;

    @ManyToOne
    private Doctor familyDoctorId;

    @OneToMany
    private List<Appointment> appointments = new ArrayList<>();
}