package com.example.demo.appointment;

import com.example.demo.doctor.DoctorRepository;
import org.apache.tomcat.jni.Local;
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

    public void addAppointment(long appId, long patientSsn, long doctorEmpId, LocalTime time, LocalDate date, int room){
        appointmentRepository.save(createAppointment(appId,patientSsn,doctorEmpId,time,date,room));
    }

    public void removeAppointment(long appId){
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
    private Appointment createAppointment(long appId, long patientSsn, long doctorEmpId,
                                          LocalTime time, LocalDate date, int room){
        return new Appointment(appId,patientSsn,doctorEmpId,time,date,room);
    }

    //a helper method to find an appointment in the repository
    public Appointment findAppointment(long appId){
        //return an appointment with the given id, else throw an exception
        return appointmentRepository.findById(appId).orElseThrow(() -> new IllegalStateException(
                "Appointment with id  " + appId + " not found."));
    }

}
