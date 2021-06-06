package com.example.demo.appointment;

import com.example.demo.exception.CustomException.InvalidIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * This class acts as an in-between for the appointmentController and the appointmentRepository.
 * This is called the "service layer".
 * @author - Justin Rackley
 */

@Service
public final class AppointmentService {

    //create a permanent reference to the appointment repository
    private final AppointmentRepository repository;

    //inject the appointment repository into this class
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
     * Get and return an appointment from the database.
     * @param appId The id of the appointment.
     * @return The appointment.
     */
    public Appointment getAppointment(final long appId){
        return find(appId);
    }

    /**
     * Book a new appointment at the hospital.
     * @param patientSsn The patient who is booking the appointment.
     * @param doctorEmpId The doctor who is being booked with.
     * @param date The date of the appointment.
     * @param room The room the appointment is in.
     */
    public HttpStatus book(final long patientSsn, final long doctorEmpId, final Date date, final int room){
        //check to make sure the doctor doesnt have another appointment at the same time
        final boolean isdoctorAvailable = doctorAvailability(doctorEmpId, date);
        //check to make sure the room will be available at the given date and time
        final boolean isRoomAvailable = roomAvailability(date, room);
        //if the doctor and room are available book the appointment
        if (isdoctorAvailable && isRoomAvailable) {
            repository.save(new Appointment(patientSsn,doctorEmpId,date,room));
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
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
            repository.findAppointmentById(appId);
        }
        catch (InvalidIdException e){
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.OK;
    }

    /**
     * Change the date of an appointment.
     * @param appId The id of the appointment.
     * @param date The new date of the appointment.
     */
    public HttpStatus changeDate(final long appId, final Date date){
        //make sure the appointment exists
        final Appointment appointment = find(appId);

        //make sure no conflict, assume same appointment time
        final boolean isDoctorAvailable = doctorAvailability(appId,date);

        //if the doctor is available set the new appointment date
        if (isDoctorAvailable) {
            appointment.setDate(date);
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }

    /**
     * Change the room an appointment is in.
     * @param appId The id of the appointment
     * @param room The new room for the appointment
     */
    public HttpStatus changeRoom(final long appId, final int room){
        //make sure the appointment exists
        Appointment appointment = find(appId);

        //make sure no conflict
        final boolean isRoomAvailable = roomAvailability(appointment.getDate(), room);

        //if the room is available change it
        if (isRoomAvailable) {
            appointment.setRoom(room);
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }

    private boolean roomAvailability(final Date date,final int room){
        //get a list of all the appointments
        final List<Appointment> appointments =  getAppointments();

        for (Appointment appointment:appointments) {
            if (!(appointment.getDate().equals(date) && appointment.getRoom() == room)){
                return true;
            }
        }
        return false;
    }

    //helper method to make sure a doctor is available at a certain date and time
    private boolean doctorAvailability(final long doctorEmpId, final Date date){
        //get a list of all an individual doctors appointments
        final List<Appointment> doctorAppointments = repository.findDoctorsAppointments(doctorEmpId);

        for (Appointment appointment:doctorAppointments) {
            if (!(appointment.getDate().equals(date))){
                return true;
            }
        }
        return false;
    }



    //a helper method to find an appointment in the repository
    private Appointment find(final long appId){
        //return an appointment with the given id, else throw an exception
        return repository.findById(appId).orElseThrow(() -> new InvalidIdException(
                "Appointment with id  " + appId + " not found."));
    }

}
