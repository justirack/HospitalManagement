package com.example.demo.appointment;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
/**
 * This class will represent an appointment in the Hospital Management System.
 * @author - Justin Rackley
 */
@Entity
@Table
public class Appointment {
    @Id
    @SequenceGenerator(name = "appId", sequenceName = "appId",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appId")
    private long appId;
    private long patientSsn;
    private long doctorEmpId;
    //how to make these non-nullable without adding @Column annotation?
    //maybe @NotNull, not sure though
    private LocalTime time;
    private LocalDate date;
    private int room;

    public Appointment(long patientSsn, long doctorEmpId, LocalTime time, LocalDate date, int room) {
        this.patientSsn = patientSsn;
        this.doctorEmpId = doctorEmpId;
        this.time = time;
        this.date = date;
        this.room = room;
    }

    public Appointment() {
    }

    /**
     * setter for the appointments time
     * @param time the appointments new time
     */
    public void setTime(LocalTime time) {
        this.time = time;
    }

    /**
     * setter for the appointments date
     * @param date the appointments new date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * setter for the appointments room
     * @param room the appointments new room
     */
    public void setRoom(int room) {
        this.room = room;
    }

    /**
     * getter for the appointment id
     * @return the appointment id
     */
    public long getAppId() {
        return appId;
    }

    /**
     * getter for the patients ssn
     * @return the patients ssn
     */
    public long getPatientSsn() {
        return patientSsn;
    }

    /**
     * getter for the doctors employee id
     * @return the doctors employee id
     */
    public long getDoctorEmpId() {
        return doctorEmpId;
    }

    /**
     * getter for the appointments time
     * @return the appointments time
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * getter for the appointments data
     * @return the appointments data
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * getter for the appointments room
     * @return the room the appointment is in
     */
    public int getRoom() {
        return room;
    }
}
