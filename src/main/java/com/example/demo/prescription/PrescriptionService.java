package com.example.demo.prescription;

import com.example.demo.exception.CustomException.InvalidIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * This class acts as an in-between for the prescriptionController and the prescriptionRepository.
 * This is called the "service layer".
 * @author - Justin Rackley
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
     * Allow a user to get a list of prescriptions from the database.
     * @return The list of prescription.
     */
    public List<Prescription> getPrescriptions(){
        //make the returned collection unmodifiable
        return Collections.unmodifiableList(repository.findAll());
    }

    /**
     * Allow a user to get a single prescription from the database.
     * @param presId The prescriptions id.
     * @return the prescription.
     */
    public Prescription getPrescription(final long presId){
        return find(presId);
    }

    /**
     * Allow a user to add a prescription to the database.
     * @param presId The prescriptions id.
     */
    public void add(final long presId){
        repository.save(find(presId));
    }

    /**
     * Allow a user to delete a prescription from the database.
     * @param presId The prescriptions id;
     */
    public void delete(final long presId){
        repository.delete(find(presId));
    }

    /**
     * Allow a user to update a prescriptions information.
     * @param presId The prescriptions id.
     * @param amount The prescriptions amount.
     */
    public void update(final long presId, final long amount){
        Prescription prescription = find(presId);

        //make sure the amount is greater than 0 and not the same as the current amount
        if (amount >= 0 && !Objects.equals(prescription.getAmount(),amount)){
            prescription.setAmount(amount);
        }
    }

    //helper method to find a prescription in the database
    private Prescription find(final long presId){
        return repository.findById(presId).orElseThrow(() -> new InvalidIdException(
                "Prescription with id  " + presId + " not found."));
    }
}
