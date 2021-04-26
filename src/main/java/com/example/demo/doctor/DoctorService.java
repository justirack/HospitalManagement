package com.example.demo.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class acts as an in-between for the doctorController and the doctorRepository.
 * This is called the "service layer".
 * @author - Justin Rackley
 */
@Service
public final class DoctorService {

    //create a permanent reference to the doctor repository
    private final DoctorRepository doctorRepository;

    //inject the doctorRepository bean into this class' bean
    @Autowired
    public DoctorService(final DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    /**
     * getter for a list of all the doctors in the database
     * @return a list of all the doctors
     */
    public List<Doctor> getDoctors(){
        return doctorRepository.findAll();
    }

    public void addDoctor(final String firstName, final String lastName, final String phone){
        doctorRepository.save(createDoctor(firstName,lastName,phone));
    }

    public void removeDoctor(final long empId){
        //check to make sure the doctor exists, will throw an exception if not
        findDoctor(empId);
        //delete the doctor from the database
        doctorRepository.deleteById(empId);
    }

    public void changeFirstName(final long empId, final String firstName) {
        //check to make sure the doctor exists, will throw an exception if not
        Doctor doctor = findDoctor(empId);
        //change the doctors first name
        doctor.setFirstName(firstName);
    }

    public void changeLastName(final long empId, final String lastName){
        //check to make sure the doctor exists, will throw an exception if not
        Doctor doctor = findDoctor(empId);
        //change the doctors last name
        doctor.setLastName(lastName);
    }

    public void changePhone(final long empId, final String phone){
        //check to make sure the doctor exists, will throw an exception if not
        Doctor doctor = findDoctor(empId);
        //change the doctors phone
        doctor.setPhone(phone);
    }

    //helper method to create a new doctor
    private Doctor createDoctor(final String firstName, final String lastName, final String phone){
        return new Doctor(firstName,lastName,phone);
    }

    //helper method to find a doctor in the database
    private Doctor findDoctor(final long empId){
        return doctorRepository.findById(empId).orElseThrow(() -> new IllegalStateException(
                "Doctor with id  " + empId + " not found."));
    }
}

