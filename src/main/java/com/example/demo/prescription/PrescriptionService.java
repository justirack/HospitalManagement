package com.example.demo.prescription;

import org.springframework.stereotype.Service;

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

    public List<Prescription> getPrescriptions(){
        return prescriptionRepository.findAll();
    }

    public void addPrescription(long presId){
        prescriptionRepository.save(findPrescription(presId));
    }

    public void deletePrescription(long presId){
        prescriptionRepository.delete(findPrescription(presId));
    }

    public void updatePrescription(long presId, long amount){
        Prescription prescription = findPrescription(presId);
    }

    private Prescription findPrescription(long presId){
        return prescriptionRepository.findById(presId).orElseThrow(() -> new IllegalStateException(
                "Prescription with id  " + presId + " not found."));
    }
}
