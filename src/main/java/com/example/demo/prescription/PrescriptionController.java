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

    /**
     * Allow a user to get a list of all prescriptions in the database.
     * @return The list of all prescriptions.
     */
    @GetMapping(path = "getPrescriptions")
    public List<Prescription> getPrescriptions(){
        return prescriptionService.getPrescriptions();
    }

    /**
     * Allow a user to add a method to the database.
     * @param presId The prescriptions id.
     */
    @PostMapping("addPrescription")
    public void addPrescription(final long presId){
        prescriptionService.addPrescription(presId);
    }

    /**
     * Allow a user to delete a prescription from the repository.
     * @param presId The prescriptions id.
     */
    @DeleteMapping("{deletePresctption}")
    public void deletePrescription(@PathVariable("deletePresctption") final long presId){
        prescriptionService.deletePrescription(presId);
    }

    /**
     * Allow a user to update a prescription.
     * @param presId The prescriptions id.
     * @param amount The prescriptions new amount.
     */
    @PutMapping("{updatePrescription}")
    public void updatePrescription(@PathVariable("updatePrescription") final long presId, final long amount){
        prescriptionService.updatePrescription(presId,amount);
    }
}
