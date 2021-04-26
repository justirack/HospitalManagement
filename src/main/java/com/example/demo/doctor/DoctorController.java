package com.example.demo.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Create a class to help serve REST endpoints and perform CRUD operations.
 * This is the "API layer" that a user interacts with.
 * This class should be accessible by: 1.Management 2.Doctors(update method only).
 * @author - Justin Rackley
 */
@RestController
@RequestMapping(path = "doctor")
public final class DoctorController {
    //create a permanent reference to doctorService
    private final DoctorService doctorService;

    //inject doctorService's bean into this class' bean
    @Autowired
    public DoctorController(final DoctorService doctorService){
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
    public void addDoctor(final String firstName, final String lastName, final String phone){
        doctorService.addDoctor(firstName,lastName,phone);
    }

    @DeleteMapping(path = "{deleteDoctor}")
    public void deleteDoctor(@PathVariable("deleteDoctor") final long doctorId){
        doctorService.removeDoctor(doctorId);
    }

    @PutMapping(path = "{updateDoctor}")
    public void updateDoctor(@PathVariable("updateDoctor") final long id,
                             @RequestParam final String firstName,
                             @RequestParam final String lastName,
                             @RequestParam final String phone){
        doctorService.changeFirstName(id, firstName);
        doctorService.changeLastName(id,lastName);
        doctorService.changePhone(id, phone);


    }

}
