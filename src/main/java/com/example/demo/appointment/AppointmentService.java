package com.example.demo.appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

/**
 * This class acts as an in-between for the appointmentController and the appointmentRepository.
 * This is called the "service layer".
 * @author - Justin Rackley
 */

@Service
public final class AppointmentService {

    private final AppointmentRepository repository;

    @Autowired
    public AppointmentService(final AppointmentRepository repository) {
        this.repository = repository;
    }

    /**
     * Get a list of all appointments at the hospital.
     * @return The list of all appointments.
     */
    public List<Appointment> getAppointments(){
        //make the returned collection unmodifiable
        return Collections.unmodifiableList(repository.findAll());
    }

    /**
     * Book a new appointment at the hospital.
     * @param patientSsn The patient who is booking the appointment.
     * @param doctorEmpId The doctor who is being booked with.
     * @param time The time of the appointment.
     * @param date The date of the appointment.
     * @param room The room the appointment is in.
     */
    public void book(final long patientSsn, final long doctorEmpId, final LocalTime time,
                     final LocalDate date, final int room){
        //check to make sure the doctor doesnt have another appointment at the same time
        boolean doctorAvailable = doctorAvailability(doctorEmpId, date, time);
        //check to make sure the room will be available at the given date and time
        boolean roomAvailable = roomAvailability(date, time, room);
        //if the doctor and room are available book the appointment
        if (doctorAvailable && roomAvailable) {
            repository.save(create(patientSsn, doctorEmpId, time, date, room));
        }
    }

    /**
     * Cancel an appointment.
     * @param appId The id of the appointment to cancel.
     */
    public void cancel(final long appId){
        //check to make sure the appointment exists, will throw an exception if it doesnt
        find(appId);
        //delete the appointment from the database
        repository.deleteById(appId);
    }

    /**
     * Change the date of an appointment.
     * @param appId The id of the appointment.
     * @param date The new date of the appointment.
     */
    public void changeDate(final long appId, final LocalDate date){
        //make sure the appointment exists
        Appointment appointment = find(appId);

        //make sure no conflict, assume same appointment time
        boolean doctorAvailable = doctorAvailability(appId,date,appointment.getTime());

        //if the doctor is available set the new appointment date
        if (doctorAvailable) {
            appointment.setDate(date);
        }
    }

    /**
     * Change the time of an appointment.
     * @param appId The id of the appointment.
     * @param time The new time of the appointment.
     */
    public void changeTime(final long appId,final  LocalTime time){
        //make sure the appointment exists
        Appointment appointment = find(appId);

        //make sure no conflict, assume same appointment data
        boolean doctorAvailable = doctorAvailability(appId,appointment.getDate(),time);

        //if the doctor is available set the new appointment time
        if (doctorAvailable) {
            appointment.setTime(time);
        }
    }

    /**
     * Change the room an appointment is in.
     * @param appId The id of the appointment
     * @param room The new room for the appointment
     */
    public void changeRoom(final long appId, final int room){
        //make sure the appointment exists
        Appointment appointment = find(appId);

        //make sure no conflict
        boolean roomAvailable = roomAvailability(appointment.getDate(), appointment.getTime(), room);

        //if the room is available change it
        if (roomAvailable) {
            appointment.setRoom(room);
            //maybe return true here if the room was successfully changed?
            //how would I use that boolean value to tell the user if the room was changed or not?
            //same thought for changing date and time
        }
    }

    private boolean roomAvailability(final LocalDate date, final LocalTime time, final int room){
        //get a list of all the appointments
        List<Appointment> appointments =  getAppointments();

        //loop through all of the appointments
        for (Appointment appointment:appointments) {
            //check if there is an appointment at the same date and time in the same room
            if (appointment.getDate().equals(date) && appointment.getTime().equals(time) &&
                    appointment.getRoom() == room){
                //return false if there is
                return false;
            }
        }
        //else return true
        return true;
    }

    //helper method to make sure a doctor is available at a certain date and time
    private boolean doctorAvailability(final long doctorEmpId, final LocalDate date, final LocalTime time){
        //get a list of all an individual doctors appointments
        List<Appointment> doctorAppointments = repository.findDoctorsAppointments(doctorEmpId);

        //go through the list and make sure there isn't one at the same data and time of the new appointment
        for (Appointment appointment:doctorAppointments) {
            if (appointment.getDate().equals(date) && appointment.getTime().equals(time)){
                //return false if there is an appointment at same data & time
                return false;
            }
        }
        //return true if no conflicting appointment
        return true;
    }

    //a helper method to create an appointment
    private Appointment create(final long patientSsn, final long doctorEmpId, final LocalTime time,
                               final LocalDate date, final int room){
        return new Appointment(patientSsn,doctorEmpId,time,date,room);
    }

    //a helper method to find an appointment in the repository
    private Appointment find(final long appId){
        //return an appointment with the given id, else throw an exception
        return repository.findById(appId).orElseThrow(() -> new IllegalStateException(
                "Appointment with id  " + appId + " not found."));
    }

}
