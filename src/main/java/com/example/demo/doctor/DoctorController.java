package com.example.demo.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
 * This is the "API layer" that a user interacts with.
 * This class should be accessible by: 1.Management 2.Doctors(update method only).
 * @author - Justin Rackley
 */
@RestController
@RequestMapping(path = "doctor")
public final class DoctorController {
    //create a permanent reference to doctorService
    private final DoctorService service;

    //inject doctorService's bean into this class' bean
    @Autowired
    public DoctorController(final DoctorService service){
        this.service = service;
    }

    /**
     * Allow a user to get a list of all doctors in the database.
     * @return A list of all doctors in the database.
     */
    @GetMapping(path = "getDoctors")
    public List<Doctor> getDoctors(){
        //make the returned collection unmodifiable
        return Collections.unmodifiableList(service.getDoctors());
    }

    /**
     * Allow a user to get a single doctor from the database.
     * @param empId The id of the doctor.
     * @return The doctor.
     */
    @GetMapping(path = "findDoctor/{empId}")
    public Doctor findDoctor(@PathVariable final long empId){
        return service.getDoctor(empId);
    }

    /**
     * Allow a user to add a doctor to the database.
     * @param firstName The doctors first name.
     * @param lastName The doctors last name.
     * @param phone The doctors phone number.
     */
    @PostMapping(path = "add")
    public void addDoctor(final String firstName, final String lastName, final String phone){
        service.add(firstName,lastName,phone);
    }

    /**
     * Allow a user to delete a doctor from the repository.
     * @param doctorId The id of the doctor to remove.
     */
    @DeleteMapping(path = "delete/{doctorId}")
    public void deleteDoctor(@PathVariable final long doctorId){
        service.remove(doctorId);
    }

    /**
     * Allow a doctor to change their first name.
     * @param id The doctors id.
     * @param firstName The doctors new first name.
     */
    @PutMapping(path = "changeFirstName/{id}/{firstName}")
    public HttpStatus changeFirstName(@PathVariable final long id, @PathVariable final String firstName){
        return service.changeFirstName(id,firstName);
    }

    /**
     * Allow a doctor to change their last name.
     * @param id The doctors id.
     * @param lastName The doctors new last name.
     */
    @PutMapping(path = "changeLastName/{id}/{lastName}")
    public HttpStatus changeLastName(@PathVariable final long id, @PathVariable final String lastName){
         return service.changeLastName(id,lastName);
    }

    /**
     * Allow a doctor to change their phone number.
     * @param id The doctors id.
     * @param phone The doctors new phone number.
     */
    @PutMapping(path = "changePhone/{id}/{phone}")
    public HttpStatus changePhone(@PathVariable final long id, @PathVariable final String phone){
        return service.changePhone(id,phone);
    }

}
