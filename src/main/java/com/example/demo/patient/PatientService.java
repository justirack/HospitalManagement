package com.example.demo.patient;

import com.example.demo.exception.CustomException.InvalidIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * This class acts as an in-between for the patientController and the patientRepository.
 * This is called the "service layer".
 * @author - Justin Rackley
 */
@Service
public final class PatientService {

    //create a permanent reference to the patient repository
    private final PatientRepository repository;

    //inject the patient repository into this class
    @Autowired
    public PatientService(final PatientRepository repository) {
        this.repository = repository;
    }

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
    public void add(final long doctorId, final String firstName, final String lastName,
                    final String phone, final String address){
        //create a new patient and save them to the database
        repository.save(new Patient(doctorId,firstName,lastName,phone,address));
    }

    /**
     * A method to allow a user to remove a patient from the database.
     * @param ssn The ssn of the patient to remove.
     */
    public void remove(final long ssn){
        //make sure the patient exists, exception will be thrown if not
        find(ssn);
        //delete the patient if no exception was thrown
        repository.deleteById(ssn);
    }

    /**
     * A method to allow a user to change a patient's name.
     * @param ssn The ssn of the patient to change.
     * @param firstName The new first name.
     */
    public void changeFirstName(final long ssn, final String firstName)
    {
        //get the patient from the database
        final Patient patient = find(ssn);

        //make sure firstName is not null, has length > 0 and is not the same as the current first name
        if (firstName != null && firstName .length()> 0 && !Objects.equals(patient.getFirstName(),firstName)){
            //set the new first name
            patient.setFirstName(firstName);
        }
    }

    public void changeLastName(final long ssn, final String lastName){
        //get the patient from the database
        final Patient patient = find(ssn);

        //make sure lastName is not null, has length > 0 and is not the same as the current last name
        if (lastName != null && lastName.length()> 0 && !Objects.equals(patient.getLastName(),lastName)){
            //set the new last name
            patient.setLastName(lastName);
        }
    }

    /**
     * A method to change a patient's family doctor.
     * @param ssn The ssn of the patient to change.
     * @param doctorId The employee id of the new family doctor.
     */
    public void changeFamilyDoctor(final long ssn, final Long doctorId){
        //get the patient from the database
        final Patient patient = find(ssn);

        //make sure doctorId is not null, has length > 0 and is not the same as the current doctor id
        if (doctorId != null && doctorId >=0 && !Objects.equals(patient.getFamilyDoctorId(),doctorId)){
            //set the patients new family doctor id
            patient.setFamilyDoctor(doctorId);
        }
    }

    /**
     * A method to change the phone number of a patient.
     * @param ssn The ssn of the patient to change.
     * @param newPhone The new phone number.
     */
    public void changePhone(final long ssn, final String newPhone){
        //get the patient from the database
        final Patient patient = find(ssn);

        //make sure newPhone is not null, has length = 10 and is not the same as the current phone number
        if (newPhone != null && newPhone.length() == 10 && !(Objects.equals(newPhone,patient.getPhone()))){
            //set the new phone number
            patient.setPhone(newPhone);
        }
    }

    /**
     * A method to change the address of a patient.
     * @param ssn The ssn of the patient to change.
     * @param newAddress The new address.
     */
    public void changeAddress(final long ssn, final String newAddress){
        //get the patient from the database
        final Patient patient = find(ssn);

        //make sure newAddress is not null, has length > 0 and is not the same as the current first name
        if (newAddress != null && newAddress.length() > 0 && !Objects.equals(patient.getAddress(),newAddress)){
            //set the new address
            patient.setAddress(newAddress);
        }
    }

    //A helper method to check if the patient exists in our database
    private Patient find(final long ssn){
        //create a new temporary patient by getting the patient with the given ssn's info from the database
        //else throw an exception

        return repository.findPatientBySsn(ssn).orElseThrow(() -> new InvalidIdException(
                "Patient with SSN " + ssn + " not found."));
    }

}
