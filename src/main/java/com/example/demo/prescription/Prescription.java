/**
 * This class will represent a prescription in the hospital management sytsem
 * @author - Justin Rackley
 */

package com.example.demo.prescription;

import com.example.demo.doctor.Doctor;
import com.example.demo.doctor.DoctorController;
import com.example.demo.patient.Patient;
import com.example.demo.patient.PatientController;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Prescription {
//    //create permanent references to the patient and doctor controllers
//    //to help make sure a patient/doctor exists before changing their info
//    private final transient PatientController patientController;
//    private final transient DoctorController doctorController;

    //a prescriptions id is its primary key
    @Id
    //prescription ids will be generated in a sequence
    @SequenceGenerator(name = "presId", sequenceName = "presId",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "presId")
    @Column(nullable = false)
    private long presId;
    @Column(nullable = false)
    private long patientSsn;
    @Column(nullable = false)
    private long doctorEmpId;
    @Column(nullable = false)
    private long amount;

    public Prescription(PatientController patientController, DoctorController doctorController,
                        long presId, long patientSsn, long doctorEmpId, long amount) {
//        this.patientController = patientController;
//        this.doctorController = doctorController;
        this.presId = presId;
        this.patientSsn = patientSsn;
        this.doctorEmpId = doctorEmpId;
        this.amount = amount;
    }

    //might not need this but want to think it over first
//    /**
//     * update the ssn of a patient who is prescribed this drug (the patient must exist)
//     * @param patientSsn the new patients ssn
//     */
//    public void setPatientSsn(long patientSsn) {
//        List<Patient> patientList = patientController.getPatients();
//
//        for (Patient patient:patientList) {
//            if (patient.getSsn() == patientSsn){
//                this.patientSsn = patientSsn;
//                break;
//            }
//        }
//    }
//
//    /**
//     * setter for the doctor who prescribed's employee id (must be a valid id)
//     * @param doctorEmpId the new doctors employee id
//     */
//    public void setDoctorEmpId(long doctorEmpId) {
//        List<Doctor> doctorList = doctorController.getDoctors();
//
//        for (Doctor doctor:doctorList) {
//            if (doctor.getEmpId() == doctorEmpId){
//                this.doctorEmpId = doctorEmpId;
//                break;
//            }
//        }
//    }

    /**
     * setter for the drug amount
     * @param amount the new amount of the drug
     */
    public void setAmount(long amount) {
        this.amount = amount;
    }

    /**
     * getter for the prescription id
     * @return the prescription id
     */
    public long getPresId() {
        return presId;
    }

    /**
     * getter for the prescribed patient's ssn
     * @return the patients ssn
     */
    public long getPatientSsn() {
        return patientSsn;
    }

    /**
     * getter for the doctor who prescribed's employee id
     * @return the doctor's employee id
     */
    public long getDoctorEmpId() {
        return doctorEmpId;
    }

    /**
     * getter for the amount of a drug to take
     * @return the amount of hte drug to take
     */
    public long getAmount() {
        return amount;
    }
}