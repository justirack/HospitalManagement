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
 * <p>
 *     This class acts as an in-between for the {@link PatientController} and the {@link PatientRepository}.
 * </p>>
 */
@Service
@RequiredArgsConstructor
public final class PatientService {

    private final PatientRepository repository;
    private final DoctorService doctorService;

    /**
     * <p>
     *     A method that will return a list of all {@link Patient} in the database to the client.
     * </p>
     * @return An unmodifiable list of patients.
     */
    public List<Patient> getPatients(){
        //make the returned collection unmodifiable
        return Collections.unmodifiableList(repository.findAll());
    }

    /**
     * <p>
     *     A method that will return a specific {@link Patient} to the client.
     * </p>
     * @param ssn The patient to return's ssn.
     * @return The Patient.
     */
    public Patient getPatient(final Long ssn){
        return find(ssn);
    }

    /**
     * <p>
     *     A method that will add a new {@link Patient} to the database.
     * </p>
     * @param doctorId The id of the patient's doctor.
     * @param firstName The patients first name.
     * @param lastName The patients last name.
     * @param phone The patients phone number.
     * @param address The patients address.
     * @return The status of if the patient was successfully added to the database.
     */
    public HttpStatus add(final Long doctorId, final String firstName, final String lastName,
                          final String phone, final String address){
        //make sure phone number is the correct length
        if (phone.length() != 10){
            throw new FailedRequestException("The phone number you enter must be 10 digits long. Please try again.");
        }

        Patient patient = new Patient();
        //set all of the patient information
        patient.setDoctor(doctorService.getDoctor(doctorId));
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setPhone(phone);
        patient.setAddress(address);

        repository.save(patient);

        //make sure the patient was added, throw an exception if not
        if (repository.findPatientByPhone(phone).isPresent()){
            return HttpStatus.OK;
        }
        throw new FailedRequestException("The new patient could not be added to the database. Please ensure all information" +
                " is correct and try again");
    }

    /**
     * <p>
     *     A method that will remove a {@link Patient} from the database.
     * </p>
     * @param ssn The ssn of the patient to remove.
     * @return The status of if the patient was successfully removed to the database.
     */
    public HttpStatus remove(final Long ssn){
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
     * <p>
     *     A method to change the first name of a {@link Patient}.
     * </p>>
     * @param ssn The ssn of the patient.
     * @param firstName The patients first name.
     */
    public void changeFirstName(final Long ssn, final String firstName)
    {
        //get the patient from the database
        final Patient patient = find(ssn);

        //make sure firstName is not null, has length > 0 and is not the same as the current first name
        if (firstName != null && !firstName.isEmpty() && !Objects.equals(patient.getFirstName(),firstName)){
            //set the new first name
            patient.setFirstName(firstName);
            return;
        }
        throw new FailedRequestException("The patients first name could not be updated." +
                " Please make sure all information is correct and try again.");
    }

    /**
     * <p>
     *     A method to change the last name of a {@link Patient}.
     * </p>
     * @param ssn The ssn of the patient.
     * @param lastName The patients new last name.
     */
    public void changeLastName(final Long ssn, final String lastName){
        //get the patient from the database
        final Patient patient = find(ssn);

        //make sure lastName is not null, has length > 0 and is not the same as the current last name
        if (lastName != null && !lastName.isEmpty() && !Objects.equals(patient.getLastName(),lastName)){
            //set the new last name
            patient.setLastName(lastName);
            return;
        }
        throw new FailedRequestException("The patients last name could not be updated." +
                " Please make sure all information is correct and try again.");
    }

    /**
     * <p>
     *     A method to change the {@link Doctor} of a {@link Patient}.
     * </p>
     * @param ssn The patients ssn.
     * @param doctorId The new doctors id.
     */
    public void changeFamilyDoctor(final Long ssn, final Long doctorId){
        //get the patient from the database
        final Patient patient = find(ssn);
        final Doctor doctor = doctorService.getDoctor(doctorId);

        //make sure doctorId is not null,  and is not the same as the current doctor id
        if ((!Objects.equals(patient.getDoctor().getId(),doctor.getId()))){
            //set the patients new family doctor id
            patient.setDoctor(doctorService.getDoctor(doctorId));
            return;
        }
        throw new FailedRequestException("The patients family doctor could not be updated." +
                " Please make sure all information is correct and try again.");

    }

    /**
     * <p>
     *     A method to change the phone number of an {@link Patient}
     * </p>
     * @param ssn the patients ssn.
     * @param newPhone the patients new phone number.
     */
    public void changePhone(final Long ssn, final String newPhone){
        //get the patient from the database
        final Patient patient = find(ssn);

        //make sure newPhone is not null, has length = 10 and is not the same as the current phone number
        if (newPhone != null && newPhone.length() == 10 && !(Objects.equals(newPhone,patient.getPhone()))){
            //set the new phone number
            patient.setPhone(newPhone);
            return;
        }
        throw new FailedRequestException("The patients phone number could not be updated." +
                " Please make sure all information is correct and try again.");
    }

    /**
     * <p>
     *     A method to change the address of a {@link Patient}.
     * </p>
     * @param ssn The patients ssn.
     * @param newAddress The patients new address.
     */
    public void changeAddress(final Long ssn, final String newAddress){
        //get the patient from the database
        final Patient patient = find(ssn);

        //make sure newAddress is not null, has length > 0 and is not the same as the current first name
        if (newAddress != null && !newAddress.isEmpty() && !Objects.equals(patient.getAddress(),newAddress)){
            //set the new address
            patient.setAddress(newAddress);
            return;
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
