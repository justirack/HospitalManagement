package com.example.demo.patient;

import com.example.demo.doctor.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private final DoctorService doctorService;

    //inject patientService's bean into this class' bean
    @Autowired
    public PatientController(final PatientService service, final DoctorService doctorService) {
        this.service = service;
        this.doctorService = doctorService;
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
     * Allow a user to get a single patient from the database.
     * @param ssn The ssn of the patient.
     * @return The patient.
     */
    @GetMapping(path = "getPatient/{ssn}")
    public Patient getPatient(@PathVariable final long ssn){
        return service.getPatient(ssn);
    }

    /**
     * Allow a user to add a new patient to the database.
     */
    @PostMapping(path = "add")
    public void addPatient(@RequestParam final long doctorId,
                           @RequestParam final String firstName,
                           @RequestParam final String lastName,
                           @RequestParam final String phone,
                           @RequestParam final String address){
        //make sure doctor's id exists, throws exception if not
        if (doctorService.getDoctor(doctorId) != null){
            service.add(doctorId,firstName,lastName,phone,address);
        }

    }

    /**
     * Allow a user to delete a person by their ssn.
     * @param ssn The person to deletes ssn.
     */
    @DeleteMapping(path = "delete/{ssn}")
    public HttpStatus deletePatient(@PathVariable final long ssn){
        return service.remove(ssn);
    }

    /**
     * Allow a user to change their first name.
     * @param ssn The patients ssn.
     * @param firstName The patients new first name.
     */
    @PutMapping(path = "changeFirstName/{ssn}/{firstName}")
    public HttpStatus changeFirstName(@PathVariable final long ssn, @PathVariable final String firstName){
        return service.changeFirstName(ssn,firstName);
    }

    /**
     * Allow a user to change their first name.
     * @param ssn The patients ssn.
     * @param lastName The patients new last name
     */
    @PutMapping(path = "changeLastName/{ssn}/{lastName}")
    public HttpStatus changeLastName(@PathVariable final long ssn, @PathVariable final String lastName){
        return service.changeLastName(ssn,lastName);
    }

    /**
     * Allow a user to change their address.
     * @param ssn The patients ssn.
     * @param address The patients new address.
     */
    @PutMapping(path = "changeAddress/{ssn}/{address}")
    public HttpStatus changeAddress(@PathVariable final long ssn, @PathVariable final String address){
        return service.changeAddress(ssn,address);
    }

    /**
     * Allow a user to change their family doctor.
     * @param ssn The patients ssn.
     * @param familyDocId The patients new family doctor's id.
     */
    @PutMapping(path = "changeDoctor/{ssn}/{familyDocId}")
    public HttpStatus changeFamilyDoctor(@PathVariable final long ssn, @PathVariable final long familyDocId){
        return service.changeFamilyDoctor(ssn,familyDocId);
    }

    /**
     * Allow a user to change their phone number.
     * @param ssn The patients ssn.
     * @param phone The users new phone number.
     */
    @PutMapping(path = "changePhone/{ssn}/{phone}")
    public HttpStatus changePhone(@PathVariable final long ssn, @PathVariable final String phone){
        return service.changePhone(ssn,phone);
    }



}
