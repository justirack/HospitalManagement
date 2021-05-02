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
    private final DoctorService doctorService;

    //inject doctorService's bean into this class' bean
    @Autowired
    public DoctorController(final DoctorService doctorService){
        this.doctorService = doctorService;
    }

    /**
     * Allow a user to get a list of all doctors in the database.
     * @return A list of all doctors in the database.
     */
    @GetMapping(path = "getDoctors")
    public List<Doctor> getDoctors(){
        //make the returned collection unmodifiable
        return Collections.unmodifiableList(doctorService.getDoctors());
    }

    /**
     * Allow a user to add a doctor to the database.
     * @param firstName The doctors first name.
     * @param lastName The doctors last name.
     * @param phone The doctors phone number.
     */
    @PostMapping(path = "add")
    public void addDoctor(final String firstName, final String lastName, final String phone){
        doctorService.add(firstName,lastName,phone);
    }

    /**
     * Allow a user to delete a doctor from the repository.
     * @param doctorId The id of the doctor to remove.
     */
    @DeleteMapping(path = "{delete}")
    public void deleteDoctor(@PathVariable("delete") final long doctorId){
        doctorService.remove(doctorId);
    }

    /**
     * Allow a user to update a doctors information.
     * @param id The doctors id.
     * @param firstName The doctors first name.
     * @param lastName The doctors last name
     * @param phone The doctors phone number
     */
    @PutMapping(path = "{update}")
    public void updateDoctor(@PathVariable("update") final long id, final String firstName,
                             final String lastName, final String phone){
        doctorService.changeFirstName(id, firstName);
        doctorService.changeLastName(id,lastName);
        doctorService.changePhone(id, phone);


    }

}
