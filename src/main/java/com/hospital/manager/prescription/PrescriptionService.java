/*
  Copyright 2021, Justin Rackley, All rights reserved.
*/
package com.hospital.manager.prescription;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.hospital.manager.doctor.Doctor;
import com.hospital.manager.patient.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hospital.manager.exception.CustomException.FailedRequestException;
import com.hospital.manager.exception.CustomException.InvalidIdException;

/**
 * <p>
 *     This class acts as an in-between for the {@link PrescriptionController} and the {@link PrescriptionRepository}.
 * </p>
 */
@Service
public final class PrescriptionService {
    //create a permanent reference to the prescriptionRepository
    private final PrescriptionRepository repository;

    //inject the prescriptionRepository into this class
    @Autowired
    public PrescriptionService(final PrescriptionRepository repository) {
        this.repository = repository;
    }

    /**
     * <p>
     *     Allow a user to get a list of {@link Prescription} from the database.
     * </p>
     * @return The list of prescription.
     */
    public List<Prescription> getPrescriptions(){
        //make the returned collection unmodifiable
        return Collections.unmodifiableList(repository.findAll());
    }

    /**
     * <p>
     *     Allow a user to get a single {@link Prescription} from the database.
     * </p>
     * @param presId The prescriptions id.
     * @return the prescription.
     */
    public Prescription getPrescription(final long presId){
        return find(presId);
    }

    /**
     * <p>
     *     Allow a user to add a {@link Prescription} to the database.
     * </p>
     * @param name The prescriptions name.
     * @param patient The patient taking the prescription.
     * @param doctor The doctor prescribing the prescription.
     * @param amount The prescriptions amount.
     * @return If the prescription was successfully added to the database.
     */
    public HttpStatus add(final String name, final Patient patient, final Doctor doctor, final Long amount){
        Prescription prescription = new Prescription();
        prescription.setName(name);
        prescription.setPatient(patient);
        prescription.setDoctor(doctor);
        prescription.setAmount(amount);

        repository.save(prescription);

        try {
            //try to find th prescription in the repository, it should be there
            repository.findPrescriptionByName(name);
        }
        //catch the exception that will be thrown if its not there
        catch (InvalidIdException e){
            throw new FailedRequestException("The prescription could not be added to the repository." +
                    " Please make sure all information is correct and try again.");
        }
        return HttpStatus.OK;
    }

    /**
     * <p>
     *     Allow a user to delete a {@link Prescription} from the database.
     * </p>
     * @param presId The prescriptions id;
     */
    public HttpStatus delete(final long presId){
        repository.delete(find(presId));

        try {
            //try to find the prescription in the repository, it should not be there
            repository.findPrescriptionByPresId(presId);
        }
        //catch the exception that should be thrown
        catch (InvalidIdException e){
            //return OK since the prescription is no longer there
            return HttpStatus.OK;
        }
        throw new FailedRequestException("The prescription could not be deleted from the repository." +
                " Please make sure all information is correct and try again.");
    }

    /**
     * <p>
     *     Allow a user to update a {@link Prescription} amount.
     * </p>
     * @param presId The prescriptions id.
     * @param amount The prescriptions amount.
     */
    public void changeAmount(final long presId, final long amount){
        Prescription prescription = find(presId);

        //make sure the amount is greater than 0 and not the same as the current amount
        if (amount >= 0 && !Objects.equals(prescription.getAmount(),amount)){
            prescription.setAmount(amount);
            return;
        }
        throw new FailedRequestException("The amount could not be updated, please make sure the prescription id" +
                " and amount are correct and try again.");
    }

    /**
     * <p>
     *     Allow a user to update a {@link Prescription} name.
     * </p>
     * @param id The prescriptions id.
     * @param name The prescriptions name.
     */
    public void changeName(final long id, final String name){
        Prescription prescription = find(id);

        if (name != null && name.length() > 0 && !Objects.equals(name,prescription.getName())){
            prescription.setName(name);
            return;
        }
        throw new FailedRequestException("The prescriptions name could not be updated. Please make sure the prescription " +
                "id and name are correct and try again");
    }

    //helper method to find a prescription in the database
    private Prescription find(final long presId){
        return repository.findById(presId).orElseThrow(() -> new InvalidIdException(
                "Prescription with id  " + presId + " not found."));
    }
}
