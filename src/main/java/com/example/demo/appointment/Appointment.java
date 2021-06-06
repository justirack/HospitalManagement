package com.example.demo.appointment;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;

/**
 * This class will represent an appointment in the Hospital Management System.
 * @author - Justin Rackley
 */
@Entity
@Table
public final class Appointment {
    @Id
    @SequenceGenerator(name = "appId", sequenceName = "appId",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appId")
    private long appId;
    private long patientSsn;
    private long doctorEmpId;
    //how to make these non-nullable without adding @Column annotation?
    //maybe @NotNull, not sure though
    private Date date;
    private int room;

    public Appointment(final long patientSsn, final long doctorEmpId, final Date date, final int room) {
        this.patientSsn = patientSsn;
        this.doctorEmpId = doctorEmpId;
        this.date = date;
        this.room = room;
    }

    public Appointment() {
    }


    /**
     * Setter for the appointments date.
     * @param date The appointments new date.
     */
    public void setDate(final Date date) {
        this.date = date;
    }

    /**
     * Setter for the appointments room.
     * @param room The appointments new room.
     */
    public void setRoom(final int room) {
        this.room = room;
    }

    /**
     * Getter for the appointment id.
     * @return The appointment id.
     */
    public long getAppId() {
        return appId;
    }

    /**
     * Getter for the patients ssn.
     * @return The patients ssn.
     */
    public long getPatientSsn() {
        return patientSsn;
    }

    /**
     * Getter for the doctors employee id.
     * @return The doctors employee id.
     */
    public long getDoctorEmpId() {
        return doctorEmpId;
    }

    /**
     * Getter for the appointments data.
     * @return The appointments data.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Getter for the appointments room.
     * @return The room the appointment is in.
     */
    public int getRoom() {
        return room;
    }
}
