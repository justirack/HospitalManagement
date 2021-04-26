package com.example.demo.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * This class acts as an in-between for the doctorController and the doctorRepository.
 * This is called the "service layer".
 * @author - Justin Rackley
 */
@Service
public final class DoctorService {

    //create a permanent reference to the doctor repository
    private final DoctorRepository repository;

    //inject the repository bean into this class' bean
    @Autowired
    public DoctorService(final DoctorRepository repository) {
        this.repository = repository;
    }

    /**
     * Getter for a list of all the doctors in the database.
     * @return A list of all the doctors.
     */
    public List<Doctor> getDoctors(){
        //make the returned collection unmodifiable
        return Collections.unmodifiableList(repository.findAll());
    }

    /**
     * Allow a user to add a doctor to the database.
     * @param firstName The doctors first name.
     * @param lastName The doctors last name.
     * @param phone The doctors phone number.
     */
    public void add(final String firstName, final String lastName, final String phone){
        repository.save(create(firstName,lastName,phone));
    }

    /**
     * Allow a user to remove a doctor from the database.
     * @param empId The doctors employee id.
     */
    public void remove(final long empId){
        //check to make sure the doctor exists, will throw an exception if not
        find(empId);
        //delete the doctor from the database
        repository.deleteById(empId);
    }

    /**
     * Allow a user to change a doctors first name.
     * @param empId The doctors employee id.
     * @param firstName The doctors new first name.
     */
    public void changeFirstName(final long empId, final String firstName) {
        //check to make sure the doctor exists, will throw an exception if not
        final Doctor doctor = find(empId);
        //change the doctors first name
        doctor.setFirstName(firstName);
    }

    /**
     * Allow a user to change a doctors last name.
     * @param empId The doctors employee id.
     * @param lastName The doctors new last name.
     */
    public void changeLastName(final long empId, final String lastName){
        //check to make sure the doctor exists, will throw an exception if not
        final Doctor doctor = find(empId);
        //change the doctors last name
        doctor.setLastName(lastName);
    }

    /**
     * Allow a user to change a doctors phone number.
     * @param empId The doctors employee id.
     * @param phone The doctors new phone number.
     */
    public void changePhone(final long empId, final String phone){
        //check to make sure the doctor exists, will throw an exception if not
        final Doctor doctor = find(empId);
        //change the doctors phone
        doctor.setPhone(phone);
    }

    //helper method to create a new doctor
    private Doctor create(final String firstName, final String lastName, final String phone){
        return new Doctor(firstName,lastName,phone);
    }

    //helper method to find a doctor in the database
    private Doctor find(final long empId){
        return repository.findById(empId).orElseThrow(() -> new IllegalStateException(
                "Doctor with id  " + empId + " not found."));
    }
}

