package com.example.demo.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    private final PatientService patientService;

    //inject patientService's bean into this class' bean
    @Autowired
    public PatientController(final PatientService patientService) {
        this.patientService = patientService;
    }


    /**
     * Allow a user to get a list of all patients in the database.
     * @return A list of all patients in teh database.
     */
    @GetMapping(path = "getPatients")
    public List<Patient> getPatients(){
        //make the returned collection unmodifiable
        return Collections.unmodifiableList(patientService.getPatients());
    }

    /**
     * Allow a user to add a new patient to the database.
     */
    @PostMapping(path = "addPatient")
    public void addPatient(@RequestBody final Patient patient){
        patientService.add(patient);
    }

    /**
     * Allow a user to delete a person by their ssn.
     * @param ssn The person to deletes ssn.
     */
    @DeleteMapping(path = "{deletePatient}")
    public void deletePatient(@PathVariable("deletePatient") final long ssn){
        patientService.remove(ssn);
    }

    /**
     * Allow a user to update a patients information.
     * @param ssn The patients ssn.
     * @param firstName The patients first name.
     * @param lastName The patients last name.
     * @param DoctorId The patients family doctors id.
     * @param phone The patients phone number.
     * @param address The patients address.
     */
    @PutMapping(path = "{updatePatient}")
    public void updatePatient(@PathVariable("updatePatient") final long ssn,
                              @RequestParam(required = false) final String firstName,
                              @RequestParam(required = false) final String lastName,
                              @RequestParam(required = false) final Long DoctorId,
                              @RequestParam(required = false) final String phone,
                              @RequestParam(required = false) final String address){
        patientService.changeName(ssn,firstName,lastName);
        patientService.changeAddress(ssn,address);
        patientService.changeFamilyDoctor(ssn,DoctorId);
        patientService.changePhone(ssn, phone);
    }


}
