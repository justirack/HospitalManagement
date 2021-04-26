package com.example.demo.appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Create a class to help serve REST endpoints and perform CRUD operations.
 * This is the "API layer" that a user interacts with.
 * This class should be accessible by: 1. Patients.
 * @author - Justin Rackley
 */

@RestController
@RequestMapping(path = "appointment")
public class AppointmentController {
    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /**
     * allow a user to get a list of all appointments
     * @return the list of all appointments
     */
    @GetMapping("getAppointments")
    public List<Appointment> getAppointments(){
        return appointmentService.getAppointments();
    }

    /**
     * allow a user to book an appointment
     * @param patientSsn the ssn of the patient booking the appointment
     * @param doctorEmpId the empId of the doctor the appointment is being booked with
     * @param time the time of the appointment
     * @param date the date of the appointment
     * @param room the room the appointment is in
     */
    @PostMapping("bookAppointment")
    public void bookAppointment(long patientSsn, long doctorEmpId, LocalTime time, LocalDate date, int room){
        appointmentService.bookAppointment(patientSsn,doctorEmpId,time,date,room);
    }

    /**
     * allow a user to cancel an appointment
     * @param appId the id of the appointment to cancel
     */
    @DeleteMapping("cancelAppointment")
    public void cancelAppointment(long appId){
        appointmentService.cancelAppointment(appId);
    }

    /**
     * allow a user to change the date of an appointment
     * @param appId the id of the appointment
     * @param date the new date of the appointment
     */
    @PutMapping("changeAppointmentDate")
    public void changeDate(long appId, LocalDate date){
        appointmentService.changeDate(appId, date);
    }

    /**
     * allow a user to change the time of an appointment
     * @param appId the id of the appointment
     * @param time the new time of the appointment
     */
    @PutMapping("changeAppointmentTime")
    public void changeTime(long appId, LocalTime time){
        appointmentService.changeTime(appId, time);
    }

    /**
     * allow a user to change the room of an appointment
     * @param appId the id of the appointment
     * @param room the new room of the appointment
     */
    @PutMapping("changeAppointmentRoom")
    public void changeRoom(long appId, int room){
        appointmentService.changeRoom(appId, room);
    }

}
