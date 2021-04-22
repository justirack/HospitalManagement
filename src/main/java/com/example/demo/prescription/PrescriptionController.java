package com.example.demo.prescription;

import com.example.demo.patient.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "prescription")
public class PrescriptionController {
    //create a permanent reference to prescriptionService
    private final PrescriptionService prescriptionService;

    @Autowired
    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @GetMapping
    public List<Prescription> getPrescriptions(){
        return prescriptionService.getPrescriptions();
    }

    @PostMapping
    public void addPrescription(long presId){
        prescriptionService.addPrescription(presId);
    }

    @DeleteMapping
    public void deletePrescription(long presId){
        prescriptionService.deletePrescription(presId);
    }

    @PutMapping
    public void updatePrescription(long presId, long amount){
        prescriptionService.updatePrescription(presId,amount);
    }
}
