/*
  Copyright 2021, Justin Rackley, All rights reserved.
*/
package com.hospital.manager.doctor;

import java.util.Collections;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hospital.manager.exception.CustomException.FailedRequestException;
import com.hospital.manager.exception.CustomException.InvalidIdException;

/**
 * This class acts as an in-between for the {@link DoctorController} and the {@link DoctorRepository}.
 */
@Service
@RequiredArgsConstructor
public final class DoctorService {

    //create a permanent reference to the doctor repository
    private final DoctorRepository repository;


    /**
     * Getter for a list of all the {@link Doctor} in the database.
     * @return an unmodifiable list of all {@link Doctor} objects. This cannot be null.
     */
    public List<Doctor> getDoctors(){
        //make the returned collection unmodifiable
        return Collections.unmodifiableList(repository.findAll());
    }

    /**
     * Allow a user to get a single {@link Doctor} from the database.
     * @param empId The id of the doctor to get.
     * @return The doctor.
     */
    public Doctor getDoctor(final long empId){
        return find(empId);
    }

    /**
     * Allow a user to add a {@link Doctor} to the database.
     * @param firstName The doctors first name.
     * @param lastName The doctors last name.
     * @param phone The doctors phone number.
     */
    public HttpStatus hire(final String firstName, final String lastName, final String phone){
        if (phone.length() != 10){
            throw new FailedRequestException("The phone number you enter must be 10 digits long. Please try again.");
        }

        Doctor doctor = new Doctor();
        doctor.setFirstName(firstName);
        doctor.setLastName(lastName);
        doctor.setPhone(phone);

        repository.save(doctor);

        if (repository.findDoctorByPhone(phone).isPresent()) {
            return HttpStatus.OK;
        }
        throw new FailedRequestException("The new doctor could not be added to the database. Please ensure all information" +
                " is correct and try again");
    }

    /**
     * Allow a user to remove a {@link Doctor} from the database.
     * @param empId The doctors employee id.
     */
    public HttpStatus remove(final long empId){
        //check to make sure the doctor exists, will throw an exception if not
        find(empId);
        //delete the doctor from the database
        repository.deleteById(empId);

        try{
            //try to find the doctor in the database, it should not be there
            repository.findDoctorByEmpId(empId);
        }
        //catch the exception that should be thrown
        catch (InvalidIdException e){
            //since the doctor was not there, return OK
            return HttpStatus.OK;
        }
        throw new FailedRequestException("The doctor could not be deleted from the database." +
                " Please make sure all information is correct and try again.");
    }

    /**
     * Allow a user to change a {@link Doctor} first name.
     * @param empId The doctors employee id.
     * @param firstName The doctors new first name.
     */
    public HttpStatus changeFirstName(final long empId, final String firstName) {
        //check to make sure the doctor exists, will throw an exception if not
        final Doctor doctor = find(empId);
        //change the doctors first name
        doctor.setFirstName(firstName);

        //make sure the update worked before returning OK
        if (doctor.getFirstName().equals(firstName)){
            return HttpStatus.OK;
        }
        throw new FailedRequestException("The doctor's first name could not be updated. " +
                "Please make sure all information is correct and try again.");
    }

    /**
     * Allow a user to change a {@link Doctor} last name.
     * @param empId The doctors employee id.
     * @param lastName The doctors new last name.
     */
    public HttpStatus changeLastName(final long empId, final String lastName){
        //check to make sure the doctor exists, will throw an exception if not
        final Doctor doctor = find(empId);
        //change the doctors last name
        doctor.setLastName(lastName);

        //make sure the update worked before returning OK
        if (doctor.getLastName().equals(lastName)){
            return HttpStatus.OK;
        }
        throw new FailedRequestException("The doctor's last name could not be updated. " +
                "Please make sure all information is correct and try again.");
    }

    /**
     * Allow a user to change a {@link Doctor} phone number.
     * @param empId The doctors employee id.
     * @param phone The doctors new phone number.
     */
    public HttpStatus changePhone(final long empId, final String phone){
        //make sure the new phone number is the correct length
        if (phone.length() != 10){
            throw new FailedRequestException("The phone number you enter must be 10 digits long. Please try again.");
        }

        //check to make sure the doctor exists, will throw an exception if not
        final Doctor doctor = find(empId);
        //change the doctors phone
        doctor.setPhone(phone);

        if (doctor.getPhone().equals(phone)) {
            return HttpStatus.OK;
        }
        throw new FailedRequestException("The doctor's phone number could not be updated. " +
                "Please make sure all information is correct and try again.");
    }

    //helper method to find a doctor in the database
    private Doctor find(final long empId){
        return repository.findById(empId).orElseThrow(() -> new InvalidIdException(
                "Doctor with id " + empId + " not found."));
    }
}

