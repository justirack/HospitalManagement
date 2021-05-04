package com.example.demo.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * Create a class to help serve REST endpoints and perform CRUD operations.
 * This is the "API layer" that a user will interact with.
 * This class should be accessible by: 1.Management 2.Patients(update method only).
 * @author - Justin Rackley
 */
@RestController
@RequestMapping(path = "patient")
public final class PatientController {

    //create a permanent reference to patient service
    private final PatientService service;

    //inject patientService's bean into this class' bean
    @Autowired
    public PatientController(final PatientService service) {
        this.service = service;
    }


    /**
     * Allow a user to get a list of all patients in the database.
     * @return A list of all patients in teh database.
     */
    @GetMapping(path = "getPatients")
    public List<Patient> getPatients(){
        //make the returned collection unmodifiable
        return Collections.unmodifiableList(service.getPatients());
    }

    /**
     * Allow a user to add a new patient to the database.
     */
    @PostMapping(path = "add")
    public void addPatient(final long doctorId, final String firstName, final String lastName,
                           final String phone, final String address){
        service.add(doctorId,firstName,lastName,phone,address);
    }

    /**
     * Allow a user to delete a person by their ssn.
     * @param ssn The person to deletes ssn.
     */
    @DeleteMapping(path = "delete/{ssn}")
    public void deletePatient(@PathVariable final long ssn){
        service.remove(ssn);
    }

    /**
     * Allow a user to change their first name.
     * @param ssn The patients ssn.
     * @param firstName The patients new first name.
     */
    @PutMapping(path = "ChangeFirstName")
    public void changeFirstName(final long ssn, final String firstName){
        service.changeFirstName(ssn,firstName);
    }

    /**
     * Allow a user to change their first name.
     * @param ssn The patients ssn.
     * @param lastName The patients new last name
     */
    @PutMapping(path = "changeLastName")
    public void changeLastName(final long ssn, final String lastName){
        service.changeLastName(ssn,lastName);
    }

    /**
     * Allow a user to change their address.
     * @param ssn The patients ssn.
     * @param address The patients new address.
     */
    @PutMapping(path = "changeAddress")
    public void changeAddress(final long ssn, final String address){
        service.changeAddress(ssn,address);
    }

    /**
     * Allow a user to change their family doctor.
     * @param ssn The patients ssn.
     * @param familyDocId The patients new family doctor's id.
     */
    @PutMapping(path = "changeDoctor")
    public void changeFamilyDoctor(final long ssn, final long familyDocId){
        service.changeFamilyDoctor(ssn,familyDocId);
    }

    /**
     * Allow a user to change their phone number.
     * @param ssn The patients ssn.
     * @param phone The users new phone number.
     */
    @PutMapping(path = "changePhone")
    public void changePhone(final long ssn, final String phone){
        service.changePhone(ssn,phone);
    }



}
