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
    private final PrescriptionRepository prescriptionRepository;

    public PrescriptionService(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    /**
     * Allow a user to get a list of prescriptions from the database.
     * @return The list of prescription.
     */
    public List<Prescription> getPrescriptions(){
        //make the returned collection unmodifiable
        return Collections.unmodifiableList(prescriptionRepository.findAll());
    }

    /**
     * Allow a user to add a prescription to the database.
     * @param presId The prescriptions id.
     */
    public void addPrescription(final long presId){
        prescriptionRepository.save(findPrescription(presId));
    }

    /**
     * Allow a user to delete a prescription from the database.
     * @param presId The prescriptions id;
     */
    public void deletePrescription(final long presId){
        prescriptionRepository.delete(findPrescription(presId));
    }

    /**
     * Allow a user to update a prescriptions information.
     * @param presId The prescriptions id.
     * @param amount The prescriptions amount.
     */
    public void updatePrescription(final long presId, final long amount){
        Prescription prescription = findPrescription(presId);
    }

    private Prescription findPrescription(final long presId){
        return prescriptionRepository.findById(presId).orElseThrow(() -> new IllegalStateException(
                "Prescription with id  " + presId + " not found."));
    }
}
