/**
 * this class acts as an in-between for the doctorController and the doctorRepository
 * this is called the "service layer"
 * @author - Justin Rackley
 */

package com.example.demo.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    //create a permanent reference to the doctor repository
    private final DoctorRepository doctorRepository;

    //inject the doctorRepository bean into this class' bean
    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    /**
     * getter for a list of all the doctors in the database
     * @return a list of all the doctors
     */
    public List<Doctor> getDoctors(){
        return doctorRepository.findAll();
    }

    public void addDoctor(String firstName, String lastName, String phone){
        doctorRepository.save(createDoctor(firstName,lastName,phone));
    }

    public void removeDoctor(long empId){
        //check to make sure the doctor exists, will throw an exception if not
        findDoctor(empId);
        //delete the doctor from the database
        doctorRepository.deleteById(empId);
    }

    public void changeFirstName(long empId, String firstName) {
        Doctor doctor = findDoctor(empId);
        doctor.setFirstName(firstName);
    }

    public void changeLastName(long empId, String lastName){
        Doctor doctor = findDoctor(empId);
        doctor.setLastName(lastName);
    }

    public void changePhone(long empId, String phone){
        Doctor doctor = findDoctor(empId);
        doctor.setPhone(phone);
    }

    private Doctor createDoctor(String firstName, String lastName, String phone){
        return new Doctor(firstName,lastName,phone);
    }

    private Doctor findDoctor(long empId){
        return doctorRepository.findById(empId).orElseThrow(() -> new IllegalStateException(
                "Doctor with id  " + empId + " not found."));
    }
}

