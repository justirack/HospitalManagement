/**
 * this class will represent an appointment in the Hospital Management System
 * @author - Justin Rackley
 */

package com.example.demo.appointment;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table
public class Appointment {
    @Id
    @SequenceGenerator(name = "appId", sequenceName = "appId",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appId")
    private long appId;
    private long patientSsn;
    private long doctorEmpId;
    private LocalTime time;
    private LocalDate date;
    private int room;

    public Appointment(long appId, long patientSsn, long doctorEmpId, LocalTime time, LocalDate date, int room) {
        this.appId = appId;
        this.patientSsn = patientSsn;
        this.doctorEmpId = doctorEmpId;
        this.time = time;
        this.date = date;
        this.room = room;
    }

    public Appointment() {
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public void setPatientSsn(long patientSsn) {
        this.patientSsn = patientSsn;
    }

    public void setDoctorEmpId(long doctorEmpId) {
        this.doctorEmpId = doctorEmpId;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public long getAppId() {
        return appId;
    }

    public long getPatientSsn() {
        return patientSsn;
    }

    public long getDoctorEmpId() {
        return doctorEmpId;
    }

    public LocalTime getTime() {
        return time;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getRoom() {
        return room;
    }
}
