package com.example.demo.appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * This class acts as an in-between for the appointmentController and the appointmentRepository.
 * This is called the "service layer".
 * @author - Justin Rackley
 */

@Service
public final class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    /**
     * get a list of all appointments at the hospital
     * @return the list of all appointments
     */
    public List<Appointment> getAppointments(){
        return appointmentRepository.findAll();
    }

    /**
     * book a new appointment at the hospital
     * @param patientSsn the patient who is booking the appointment
     * @param doctorEmpId the doctor who is being booked with
     * @param time the time of the appointment
     * @param date the date of the appointment
     * @param room the room the appointment is in
     */
    public void bookAppointment(long patientSsn, long doctorEmpId, LocalTime time, LocalDate date, int room){
        //check to make sure the doctor doesnt have another appointment at the same time
        boolean doctorAvailable = doctorAvailability(doctorEmpId, date, time);
        //check to make sure the room will be available at the given date and time
        boolean roomAvailable = roomAvailability(date, time, room);
        //if the doctor and room are available book the appointment
        if (doctorAvailable && roomAvailable) {
            appointmentRepository.save(createAppointment(patientSsn, doctorEmpId, time, date, room));
        }
    }

    /**
     * cancel an appointment
     * @param appId the id of the appointment to cancel
     */
    public void cancelAppointment(long appId){
        //check to make sure the appointment exists, will throw an exception if it doesnt
        findAppointment(appId);
        //delete the appointment from the database
        appointmentRepository.deleteById(appId);
    }

    /**
     * change the date of an appointment
     * @param appId the id of the appointment
     * @param date the new date of the appointment
     */
    public void changeDate(long appId, LocalDate date){
        //make sure the appointment exists
        Appointment appointment = findAppointment(appId);

        //make sure no conflict, assume same appointment time
        boolean doctorAvailable = doctorAvailability(appId,date,appointment.getTime());

        //if the doctor is available set the new appointment date
        if (doctorAvailable) {
            appointment.setDate(date);
        }
    }

    /**
     * change the time of an appointment
     * @param appId the id of the appointment
     * @param time the new time of the appointment
     */
    public void changeTime(long appId, LocalTime time){
        //make sure the appointment exists
        Appointment appointment = findAppointment(appId);

        //make sure no conflict, assume same appointment data
        boolean doctorAvailable = doctorAvailability(appId,appointment.getDate(),time);

        //if the doctor is available set the new appointment time
        if (doctorAvailable) {
            appointment.setTime(time);
        }
    }

    /**
     * change the room an appointment is in
     * @param appId the if of the appointment
     * @param room the new room for the appointment
     */
    public void changeRoom(long appId, int room){
        //make sure the appointment exists
        Appointment appointment = findAppointment(appId);

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

    private boolean roomAvailability(LocalDate date, LocalTime time, int room){
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
    private boolean doctorAvailability(long doctorEmpId, LocalDate date, LocalTime time){
        //get a list of all an individual doctors appointments
        List<Appointment> doctorAppointments = appointmentRepository.findDoctorsAppointments(doctorEmpId);

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
    private Appointment createAppointment(long patientSsn, long doctorEmpId,
                                          LocalTime time, LocalDate date, int room){
        return new Appointment(patientSsn,doctorEmpId,time,date,room);
    }

    //a helper method to find an appointment in the repository
    private Appointment findAppointment(long appId){
        //return an appointment with the given id, else throw an exception
        return appointmentRepository.findById(appId).orElseThrow(() -> new IllegalStateException(
                "Appointment with id  " + appId + " not found."));
    }

}
