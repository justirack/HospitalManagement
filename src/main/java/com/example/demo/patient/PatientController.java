/**
 * create a class to help serve REST enpoints
 * and perform CRUD operations
 * @author - Justin Rackley
 */

package com.example.demo.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//allow the class to serve REST endpoints
@RestController
//create a new page for patients at localhost:8080/patient
@RequestMapping(path = "patient")
public class PatientController {

    //create a permanent reference to patient service
    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public List<Patient> getPatients(){
        return patientService.getPatients();
    }

    @PostMapping
    public void addNewPatient(@RequestBody Patient patient){
        patientService.addNewPatient(patient);
    }

    @DeleteMapping(path = "patientSsn")
    public void deletePatient(@PathVariable("patientSsn")Long ssn){
        patientService.removePatientBySsn(ssn);
    }

    @PutMapping(path = "patientSsn")
    public void updatePatient(@PathVariable("patientSsn") Long ssn,
                              @RequestParam(required = false) String firstName,
                              @RequestParam(required = false) String lastName,
                              @RequestParam(required = false) String familyDoctor,
                              @RequestParam(required = false) String phone,
                              @RequestParam(required = false) String address){
        patientService.changeName(ssn,firstName,lastName);
        patientService.changeAddress(ssn,address);
        patientService.changeFamilyDoctor(ssn,familyDoctor);
        patientService.changePhone(ssn, phone);
    }


}
