package com.example.demo.prescription;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Create a class to help serve REST endpoints and perform CRUD operations.
 * This is the "API layer" that a user will interact with.
 * This class should only be accessible by: 1.Doctors.
 * @author - Justin Rackley
 */
@Controller
@RequestMapping(path = "prescription")
public class PrescriptionController {
    //create a permanent reference to prescriptionService
    private final PrescriptionService prescriptionService;

    @Autowired
    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @GetMapping(path = "getPrescriptions")
    public List<Prescription> getPrescriptions(){
        return prescriptionService.getPrescriptions();
    }

    @PostMapping("addPrescription")
    public void addPrescription(long presId){
        prescriptionService.addPrescription(presId);
    }

    @DeleteMapping("{deletePresctption}")
    public void deletePrescription(@PathVariable("deletePresctption") long presId){
        prescriptionService.deletePrescription(presId);
    }

    @PutMapping("{updatePrescription}")
    public void updatePrescription(@PathVariable("updatePrescription") long presId, long amount){
        prescriptionService.updatePrescription(presId,amount);
    }
}
