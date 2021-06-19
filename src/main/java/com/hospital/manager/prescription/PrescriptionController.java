/*
  Copyright 2021, Justin Rackley, All rights reserved.
*/
package com.hospital.manager.prescription;


import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private final PrescriptionService service;

    @Autowired
    public PrescriptionController(PrescriptionService service) {
        this.service = service;
    }

    /**
     * Allow a user to get a list of all prescriptions in the database.
     * @return The list of all prescriptions.
     */
    @GetMapping(path = "getPrescriptions")
    public List<Prescription> getPrescriptions(){
        //make the returned collection unmodifiable
        return Collections.unmodifiableList(service.getPrescriptions());
    }

    /**
     * Allow a user to get a single prescription from the database.
     * @param presId The prescriptions id.
     * @return The prescription.
     */
    @GetMapping(path = "getPrescription/{presId}")
    public Prescription getPrescription(@PathVariable final long presId){
        return service.getPrescription(presId);
    }

    /**
     * Allow a user to add a method to the database.
     * @param presId The prescriptions id.
     */
    @PostMapping("add")
    public HttpStatus add(final long presId){
        return service.add(presId);
    }

    /**
     * Allow a user to delete a prescription from the repository.
     * @param presId The prescriptions id.
     */
    @DeleteMapping("delete/{presId}")
    public HttpStatus delete(@PathVariable final long presId){
        return service.delete(presId);
    }

    /**
     * Allow a user to update a prescription.
     * @param presId The prescriptions id.
     * @param amount The prescriptions new amount.
     */
    @PutMapping("update/{presId}/{amount}")
    public HttpStatus changeAmount(@PathVariable final long presId, @PathVariable final long amount){
        return service.update(presId,amount);
    }
}
