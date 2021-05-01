package com.example.demo.appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

/**
 * Create a class to help serve REST endpoints and perform CRUD operations.
 * This is the "API layer" that a user interacts with.
 * This class should be accessible by: 1. Patients.
 * @author - Justin Rackley
 */

@RestController
@RequestMapping(path = "appointment")
public final class AppointmentController {
    private final AppointmentService service;

    @Autowired
    public AppointmentController(final AppointmentService service) {
        this.service = service;
    }

    /**
     * Allow a user to get a list of all appointments.
     * @return The list of all appointments.
     */
    @GetMapping("getAppointments")
    public List<Appointment> getAppointments(){
        return Collections.unmodifiableList(service.getAppointments());
    }

    /**
     * Allow a user to book an appointment.
     * @param patientSsn The ssn of the patient booking the appointment.
     * @param doctorEmpId The empId of the doctor the appointment is being booked with.
     * @param time The time of the appointment.
     * @param date The date of the appointment.
     * @param room The room the appointment is in.
     */
    @PostMapping("book")
    public void book(final long patientSsn, final long doctorEmpId, final LocalTime time,
                     final LocalDate date, final int room){
        service.book(patientSsn,doctorEmpId,time,date,room);
    }

    /**
     * Allow a user to cancel an appointment.
     * @param appId The id of the appointment to cancel.
     */
    @DeleteMapping("cancel")
    public void cancel(final long appId){
        service.cancel(appId);
    }

    /**
     * Allow a user to change the date of an appointment.
     * @param appId The id of the appointment.
     * @param date The new date of the appointment.
     */
    @PutMapping("changeDate")
    public void changeDate(final long appId, final LocalDate date){
        service.changeDate(appId, date);
    }

    /**
     * Allow a user to change the time of an appointment.
     * @param appId The id of the appointment.
     * @param time The new time of the appointment.
     */
    @PutMapping("changeTime")
    public void changeTime(final long appId, final LocalTime time){
        service.changeTime(appId, time);
    }

    /**
     * Allow a user to change the room of an appointment.
     * @param appId The id of the appointment.
     * @param room The new room of the appointment.
     */
    @PutMapping("changeRoom")
    public void changeRoom(final long appId, final int room){
        service.changeRoom(appId, room);
    }

}
