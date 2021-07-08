/*
  Copyright 2021, Justin Rackley, All rights reserved.
*/
package com.hospital.manager.appointment;

import java.text.SimpleDateFormat;
import java.util.Date;

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
 * This class represents an appointment within the Hospital Management System.
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public final class Appointment
{
    /**
     * @return The formatted string representation of the {@link #getDate()} value.
     *         The string returned will be in the 'yyyy-MM-dd hh:mm' format. This
     *         cannot be null.
     */
    String getFormattedDate()
    {
        // The date cannot be null on an appointment, so the following operation
        // should always succeed.
        return FORMATTER.format(date);
    }

    /**
     * The unique database identifier for this appointment. This cannot be null,
     * but it can be set to 0L if this object has never been persisted to the database
     * yet.
     */
    @Id
    @SequenceGenerator(name = "appointment_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appointment_sequence")
    private long id;

    /**
     * The {@link Patient} that is associated with this appointment. This cannot be
     * null.
     */
    @ManyToOne
    private Patient patient;

    /**
     * The {@link Doctor} that will attend to the {@link Patient} at this appointment.
     * This cannot be null.
     */
    @ManyToOne
    private Doctor doctor;

    /**
     * The date and time when this appointment is scheduled. This cannot be null.
     */
    @Column(nullable = false)
    private Date date;

    /**
     * The room number for where this appointment takes place. This cannot be null.
     */
    private int room;

    // A date formatter used to produce string representations for the date objects
    // contained within this class. This formatter captures the date and time (
    // to the minute only). This does not take into account timezones.
    private static final SimpleDateFormat FORMATTER
        = new SimpleDateFormat("yyyy-MM-dd hh:mm");
}