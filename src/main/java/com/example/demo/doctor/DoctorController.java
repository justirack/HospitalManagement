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

    @Autowired
    public DoctorController(DoctorService doctorService){
        this.doctorService = doctorService;
    }

    @GetMapping
    public List<Doctor> getDoctors(){
        return doctorService.getDoctors();
    }

    @PostMapping
    public void addDoctor(@RequestBody Doctor doctor){
        doctorService.addDoctor(doctor);
    }

    @DeleteMapping(path = "{doctorId}")
    public void deleteDoctor(@PathVariable("doctorId") Long doctorId){
        doctorService.removeDoctor(doctorId);
    }

    @PutMapping(path = "{doctorId}")
    public void updateDoctor(@PathVariable("doctorId") Long id,
                             @RequestParam String firstName,
                             @RequestParam String lastName,
                             @RequestParam String phone){
        doctorService.changeName(id, firstName, lastName);
        doctorService.changePhone(id, phone);


    }

}
