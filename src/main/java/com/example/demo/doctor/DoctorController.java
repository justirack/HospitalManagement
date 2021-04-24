/**
 * create a class to help serve REST endpoints and perform CRUD operations
 * this is the "API layer" that a user interacts with
 * this class should be accessible by: 1.Management 2.Doctors(update method only)
 * @author - Justin Rackley
 */

package com.example.demo.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//allows the class to serve REST endpoints
@RestController
//create a new page for doctors at localhost:8080/doctor
@RequestMapping(path = "doctor")
public class DoctorController {
    //create a permanent reference to doctorService
    private final DoctorService doctorService;

    //inject doctorService's bean into this class' bean
    @Autowired
    public DoctorController(DoctorService doctorService){
        this.doctorService = doctorService;
    }

    /**
     * allow a user to get a list of all doctors in the database
     * @return a list of all doctors in the database
     */
    @GetMapping(path = "getDoctors")
    public List<Doctor> getDoctors(){
        return doctorService.getDoctors();
    }

    @PostMapping(path = "addDoctor")
    public void addDoctor(String firstName, String lastName, String phone){
        doctorService.addDoctor(firstName,lastName,phone);
    }

    @DeleteMapping(path = "{deleteDoctor}")
    public void deleteDoctor(@PathVariable("deleteDoctor") long doctorId){
        doctorService.removeDoctor(doctorId);
    }

    @PutMapping(path = "{updateDoctor}")
    public void updateDoctor(@PathVariable("updateDoctor") long id,
                             @RequestParam String firstName,
                             @RequestParam String lastName,
                             @RequestParam String phone){
        doctorService.changeFirstName(id, firstName);
        doctorService.changeLastName(id,lastName);
        doctorService.changePhone(id, phone);


    }

}
