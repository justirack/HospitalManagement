/**
 * create a class to help serve REST endpoints and perform CRUD operations
 * this is the "API layer" that a user interacts with
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

    /**
     * allow a user to add a doctor to the database
     * @param doctorId the doctor to add to the database's id
     */
    @PostMapping(path = "addDoctor")
    public void addDoctor(@RequestBody long doctorId){
        doctorService.addDoctor(doctorId);
    }

    /**
     * allow a user to delete a doctor from the database
     * @param doctorId the doctor to remove from the database
     */
    @DeleteMapping(path = "deleteDoctor")
    public void deleteDoctor(@PathVariable("doctorId") long doctorId){
        doctorService.removeDoctor(doctorId);
    }

    /**
     * allow a user to update a doctors information
     * @param id the doctors employee id
     * @param firstName the doctors first name
     * @param lastName the doctors last name
     * @param phone the doctors phone number
     */
    @PutMapping(path = "updateDoctor")
    public void updateDoctor(@PathVariable("doctorId") long id,
                             @RequestParam String firstName,
                             @RequestParam String lastName,
                             @RequestParam String phone){
        doctorService.changeName(id, firstName, lastName);
        doctorService.changePhone(id, phone);


    }

}
