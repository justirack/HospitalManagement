/*
  Copyright 2021, Justin Rackley, All rights reserved.
*/
package com.hospital.manager.patient;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.hospital.manager.doctor.Doctor;
import com.hospital.manager.doctor.DoctorRepository;
import com.hospital.manager.doctor.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hospital.manager.exception.CustomException.FailedRequestException;
import com.hospital.manager.exception.CustomException.InvalidIdException;

/**
 * This class acts as an in-between for the patientController and the patientRepository.
 * This is called the "service layer".
 * @author - Justin Rackley
 */
@Service
@RequiredArgsConstructor
public final class PatientService {

    private final PatientRepository repository;
    private final DoctorService doctorService;

    /**
     * A getter for a list of the patients in the database.
     * @return A list of all of the patients in the database.
     */
    public List<Patient> getPatients(){
        //make the returned collection unmodifiable
        return Collections.unmodifiableList(repository.findAll());
    }

    /**
     * Allow a user to get a single patient from the database.
     * @param ssn The ssn of the patient to find.
     * @return The patient.
     */
    public Patient getPatient(final long ssn){
        return find(ssn);
    }

    /**
     * add a patient to the database
     * @param doctorId the patients family doctor's id
     * @param firstName the patients first name
     * @param lastName the patients last name
     * @param phone the patients phone number
     * @param address the patients address
     */
    public HttpStatus add(final long doctorId, final String firstName, final String lastName,
                          final String phone, final String address){
        Patient patient = new Patient();

        patient.setDoctor(doctorService.getDoctor(doctorId));
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setPhone(phone);
        patient.setAddress(address);

        repository.save(patient);

        if (repository.findPatientByPhone(phone).isPresent()){
            return HttpStatus.OK;
        }
        throw new FailedRequestException("The new patient could not be added to the database. Please ensure all information" +
                " is correct and try again");
    }

    /**
     * A method to allow a user to remove a patient from the database.
     * @param ssn The ssn of the patient to remove.
     */
    public HttpStatus remove(final long ssn){
        //make sure the patient exists, exception will be thrown if not
        find(ssn);
        //delete the patient if no exception was thrown
        repository.deleteById(ssn);

        try {
            //try to find the patient in the database, they should not be there
            repository.findPatientBySsn(ssn);
        }
        //catch the exception that should be thrown
        catch (InvalidIdException e){
            //return OK since they are no longer there
            return HttpStatus.OK;
        }
        throw new FailedRequestException("The patient could not be deleted from the database." +
                " Please make sure all information is correct and try again.");
    }

    /**
     * A method to allow a user to change a patient's name.
     * @param ssn The ssn of the patient to change.
     * @param firstName The new first name.
     */
    public HttpStatus changeFirstName(final long ssn, final String firstName)
    {
        //get the patient from the database
        final Patient patient = find(ssn);

        //make sure firstName is not null, has length > 0 and is not the same as the current first name
        if (firstName != null && !firstName.isEmpty() && !Objects.equals(patient.getFirstName(),firstName)){
            //set the new first name
            patient.setFirstName(firstName);
            return HttpStatus.OK;
        }
        throw new FailedRequestException("The patients first name could not be updated." +
                " Please make sure all information is correct and try again.");
    }

    public HttpStatus changeLastName(final long ssn, final String lastName){
        //get the patient from the database
        final Patient patient = find(ssn);

        //make sure lastName is not null, has length > 0 and is not the same as the current last name
        if (lastName != null && !lastName.isEmpty() && !Objects.equals(patient.getLastName(),lastName)){
            //set the new last name
            patient.setLastName(lastName);
            return HttpStatus.OK;
        }
        throw new FailedRequestException("The patients last name could not be updated." +
                " Please make sure all information is correct and try again.");
    }

    /**
     * A method to change a patient's family doctor.
     * @param ssn The ssn of the patient to change.
     * @param doctorId The employee id of the new family doctor.
     */
    public HttpStatus changeFamilyDoctor(final long ssn, final Long doctorId){
        //get the patient from the database
        final Patient patient = find(ssn);
        final Doctor doctor = doctorService.getDoctor(doctorId);

        //make sure doctorId is not null,  and is not the same as the current doctor id
        if ((!Objects.equals(patient.getDoctor().getId(),doctor.getId()))){
            //set the patients new family doctor id
            patient.setDoctor(doctorService.getDoctor(doctorId));
            return HttpStatus.OK;
        }
        throw new FailedRequestException("The patients family doctor could not be updated." +
                " Please make sure all information is correct and try again.");

    }

    /**
     * A method to change the phone number of a patient.
     * @param ssn The ssn of the patient to change.
     * @param newPhone The new phone number.
     */
    public HttpStatus changePhone(final long ssn, final String newPhone){
        //get the patient from the database
        final Patient patient = find(ssn);

        //make sure newPhone is not null, has length = 10 and is not the same as the current phone number
        if (newPhone != null && newPhone.length() == 10 && !(Objects.equals(newPhone,patient.getPhone()))){
            //set the new phone number
            patient.setPhone(newPhone);
            return HttpStatus.OK;
        }
        throw new FailedRequestException("The patients phone number could not be updated." +
                " Please make sure all information is correct and try again.");
    }

    /**
     * A method to change the address of a patient.
     * @param ssn The ssn of the patient to change.
     * @param newAddress The new address.
     */
    public HttpStatus changeAddress(final long ssn, final String newAddress){
        //get the patient from the database
        final Patient patient = find(ssn);

        //make sure newAddress is not null, has length > 0 and is not the same as the current first name
        if (newAddress != null && !newAddress.isEmpty() && !Objects.equals(patient.getAddress(),newAddress)){
            //set the new address
            patient.setAddress(newAddress);
            return HttpStatus.OK;
        }
        throw new FailedRequestException("The patients address could not be updated." +
                " Please make sure all information is correct and try again.");
    }

    //A helper method to check if the patient exists in our database
    private Patient find(final long ssn){
        //create a new temporary patient by getting the patient with the given ssn's info from the database
        //else throw an exception

        return repository.findPatientBySsn(ssn).orElseThrow(() -> new InvalidIdException(
                "Patient with SSN " + ssn + " not found."));
    }

}
