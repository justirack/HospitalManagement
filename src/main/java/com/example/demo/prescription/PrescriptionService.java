package com.example.demo.prescription;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * This class acts as an in-between for the prescriptionController and the prescriptionRepository.
 * This is called the "service layer".
 * @author - Justin Rackley
 */
@Service
public final class PrescriptionService {
    //create a permanent reference to the prescriptionRepository
    private final PrescriptionRepository repository;

    public PrescriptionService(PrescriptionRepository prescriptionRepository) {
        this.repository = prescriptionRepository;
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
    }

    private Prescription find(final long presId){
        return repository.findById(presId).orElseThrow(() -> new IllegalStateException(
                "Prescription with id  " + presId + " not found."));
    }
}
