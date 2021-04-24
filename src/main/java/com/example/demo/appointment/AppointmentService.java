package com.example.demo.appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> getAppointments(){
        return appointmentRepository.findAll();
    }

    public void addAppointment(long patientSsn, long doctorEmpId, LocalTime time, LocalDate date, int room){
        appointmentRepository.save(createAppointment(patientSsn,doctorEmpId,time,date,room));
    }

    public void removeAppointment(long appId){
        //check to make sure the appointment exists, will throw an exception if it doesnt
        findAppointment(appId);
        //delete the appointment from the repository
        appointmentRepository.deleteById(appId);
    }

    public void updateDate(long appId, LocalDate date){
        Appointment appointment = findAppointment(appId);
        appointment.setDate(date);
    }

    public void updateTime(long appId, LocalTime time){
        Appointment appointment = findAppointment(appId);
        appointment.setTime(time);
    }

    public void updateRoom(long appId, int room){
        Appointment appointment = findAppointment(appId);
        appointment.setRoom(room);
    }

    //a helper method to create an appointment
    private Appointment createAppointment(long patientSsn, long doctorEmpId,
                                          LocalTime time, LocalDate date, int room){
        return new Appointment(patientSsn,doctorEmpId,time,date,room);
    }

    //a helper method to find an appointment in the repository
    public Appointment findAppointment(long appId){
        //return an appointment with the given id, else throw an exception
        return appointmentRepository.findById(appId).orElseThrow(() -> new IllegalStateException(
                "Appointment with id  " + appId + " not found."));
    }

}
