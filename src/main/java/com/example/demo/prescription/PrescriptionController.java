package com.example.demo.prescription;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Create a class to help serve REST endpoints and perform CRUD operations.
 * This is the "API layer" that a user will interact with.
 * This class should only be accessible by: 1.Doctors.
 * @author - Justin Rackley
 */
@RestController
@RequestMapping(path = "prescription")
public final class PrescriptionController {
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
