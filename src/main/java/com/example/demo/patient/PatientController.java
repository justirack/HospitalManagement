/**
 * create a class to help serve REST endpoints and perform CRUD operations
 * this is the "API layer" that a user will interact with
 * @author - Justin Rackley
 */

package com.example.demo.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
this class should be accessible by
1.doctors
2.management
3. a patient should be able to change their own information, no other methods
 */

//allow the class to serve REST endpoints
@RestController
//create a new page for patients at localhost:8080/patient
@RequestMapping(path = "patient")
public class PatientController {

    //create a permanent reference to patient service
    private final PatientService patientService;

    //inject patientService's bean into this class' bean
    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }


    /**
     * allow a user to get a list of all patients in the database
     * @return a list of all patients in teh database
     */
    @GetMapping(path = "getPatients")
    public List<Patient> getPatients(){
        return patientService.getPatients();
    }

    /**
     * allow a user to add a new patient to the database
     */
    @PostMapping(path = "addPatient")
    public void addPatient(@RequestBody long ssn){

        patientService.addNewPatient(ssn);
    }

    /**
     * allow a user to delete a person by their ssn
     * @param ssn the person to deletes ssn
     */
    @DeleteMapping(path = "deletePatient")
    public void deletePatient(@PathVariable("deletePatient")long ssn){
        patientService.removePatient(ssn);
    }

    /**
     * allow a user to update a patients information
     * @param ssn the patients ssn
     * @param firstName the patients first name
     * @param lastName the patients last name
     * @param DoctorId the patients family doctors id
     * @param phone the patients phone number
     * @param address the patients address
     */
    @PutMapping(path = "updatePatient")
    public void updatePatient(@PathVariable("patientSsn") long ssn,
                              @RequestParam(required = false) String firstName,
                              @RequestParam(required = false) String lastName,
                              @RequestParam(required = false) Long DoctorId,
                              @RequestParam(required = false) String phone,
                              @RequestParam(required = false) String address){
        patientService.changeName(ssn,firstName,lastName);
        patientService.changeAddress(ssn,address);
        patientService.changeFamilyDoctor(ssn,DoctorId);
        patientService.changePhone(ssn, phone);
    }


}
