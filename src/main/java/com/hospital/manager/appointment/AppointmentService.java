/*
  Copyright 2021, Justin Rackley, All rights reserved.
*/
package com.hospital.manager.appointment;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.hospital.manager.doctor.DoctorService;
import com.hospital.manager.patient.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hospital.manager.exception.CustomException.FailedRequestException;
import com.hospital.manager.exception.CustomException.InvalidIdException;

import lombok.RequiredArgsConstructor;

/**
 * This class acts as an in-between for the {@link AppointmentController} and the {@link AppointmentRepository}.
 */
@Service
@RequiredArgsConstructor
public final class AppointmentService
{
    // --------------------------------------------------------------------
    // :: Public Interface
    /**
     * Retrieves a list of all appointments at this hospital.
     * 
     * @return An unmodifiable list of all {@link Appointment} objects.
     *         This cannot be null.
     */
    public List<Appointment> getAppointments()
    {
        return Collections.unmodifiableList(repository.findAll());
    }

    /**
     * Get and return an appointment from the database.
     * @param id The id of the appointment.
     * @return The appointment.
     */
    public Appointment getAppointment(final long id)
    {
        return find(id);
    }

    /**
     * Book a new appointment at the hospital.
     * @param patientSsn The patient who is booking the appointment.
     * @param doctorId The doctor who is being booked with.
     * @param date The date of the appointment.
     * @param room The room the appointment is in.
     */
    public HttpStatus book(
        final long patientSsn,
        final long doctorId,
        final Date date,
        final int room) {
        //check to make sure the doctor doesnt have another appointment at the same time
        final boolean isDoctorAvailable = doctorAvailability(doctorId, date);

        //check to make sure the room will be available at the given date and time
        final boolean isRoomAvailable = roomAvailability(date, room);

        //if the doctor and room are available book the appointment
        if (isDoctorAvailable && isRoomAvailable) {
            Appointment appointment = new Appointment();
            appointment.setPatient(patientService.getPatient(patientSsn));
            appointment.setDoctor(doctorService.getDoctor(doctorId));
            appointment.setDate(date);
            appointment.setRoom(room);

            repository.save(appointment);
            return HttpStatus.OK;
        }

        //throw an exception if either the doctor or room is unavailable
        throw new FailedRequestException("Either the doctor or room requested at " + date +
                ". Please try another date or time.");
    }

    /**
     * Cancel an appointment.
     * @param appId The id of the appointment to cancel.
     */
    public HttpStatus cancel(final long appId){
        //check to make sure the appointment exists, will throw an exception if it doesnt
        find(appId);
        //delete the appointment from the database
        repository.deleteById(appId);

        try {
            //try to find the appointment, it should not be there
            repository.findAppointmentById(appId);
        }
        //catch the exception that should be thrown
        catch (InvalidIdException e){
            //return OK since the appointment is not in the database
            return HttpStatus.OK;
        }
        //throw an exception if the appointment is still there
        throw new FailedRequestException("The appointment could not be deleted. Please make sure all" +
                " information is correct and try again.");
    }

    /**
     * Change the date of an appointment.
     * @param appId The id of the appointment.
     * @param date The new date of the appointment.
     */
    public void changeDate(final long appId, final Date date){
        //make sure the appointment exists
        final Appointment appointment = find(appId);

        //make sure no conflict, assume same appointment time
        final boolean isDoctorAvailable = doctorAvailability(appId,date);

        //make sure the room is still available on the new date
        final boolean isRoomAvailable = roomAvailability(date, appointment.getRoom());

        //if the doctor and room are available, set the new appointment date
        if (isDoctorAvailable && isRoomAvailable) {
            appointment.setDate(date);
            return;
        }
        //throw an exception if the doctor or room is not available
        throw new FailedRequestException("Either the doctor or room requested at " + date +
                ". Please try another date or time.");
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
        final boolean isRoomAvailable = roomAvailability(appointment.getDate(), room);

        //if the room is available change it
        if (isRoomAvailable) {
            appointment.setRoom(room);
            return;
        }
        throw new FailedRequestException("Room " + room + " is not available at " + appointment.getDate() +
                " please try to book another room or change your appointment date.");
    }

    // --------------------------------------------------------------------
    // :: Private Interface

    private boolean roomAvailability(final Date date,final int room){
        //get a list of all the appointments
        final List<Appointment> appointments =  getAppointments();

        for (Appointment appointment:appointments) {
            if (!(appointment.getDate().equals(date) && appointment.getRoom() == room)){
                return false;
            }
        }
        return true;
    }

    //helper method to make sure a doctor is available at a certain date and time
    private boolean doctorAvailability(final long doctorEmpId, final Date date){
        //get a list of all an individual doctors appointments
        final List<Appointment> doctorAppointments = repository.findDoctorsAppointments(doctorEmpId);

        for (Appointment appointment:doctorAppointments) {
            if (!(appointment.getDate().equals(date))){
                return false;
            }
        }
        return true;
    }

    //a helper method to find an appointment in the repository
    private Appointment find(final long appId){
        //return an appointment with the given id, else throw an exception
        return repository.findById(appId).orElseThrow(() -> new InvalidIdException(
                "Appointment with id  " + appId + " not found."));
    }

    // --------------------------------------------------------------------
    // :: Private Members
    //create a permanent reference to the appointment repository
    private final AppointmentRepository repository;
    private final DoctorService doctorService;
    private final PatientService patientService;
}