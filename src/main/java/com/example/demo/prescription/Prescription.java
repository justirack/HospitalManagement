/**
 * This class will represent a prescription in the hospital management sytsem
 * @author - Justin Rackley
 */

package com.example.demo.prescription;

import com.example.demo.patient.PatientService;

import javax.persistence.*;

@Entity
@Table
public class Prescription {
    //a prescriptions id is its primary key
    @Id
    //prescription ids will be generated in a sequence
    @SequenceGenerator(name = "presId", sequenceName = "presId",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "presId")
    @Column(nullable = false)
    private String presId;
    @Column(nullable = false)
    private long patientSsn;
    @Column(nullable = false)
    private long doctorEmpId;
    @Column(nullable = false)
    private long amount;

    public Prescription(String presId, long patientSsn, long doctorEmpId, long amount) {
        this.presId = presId;
        this.patientSsn = patientSsn;
        this.doctorEmpId = doctorEmpId;
        this.amount = amount;
    }

    public Prescription() {
    }

    /**
     * update the ssn of a patient who is prescribed this drug (the patient must exist)
     * @param patientSsn the new patients ssn
     */
    public void setPatientSsn(long patientSsn) {
        this.patientSsn = patientSsn;
    }

    /**
     * setter for the doctor who prescribed's employee id (must be a valid id)
     * @param doctorEmpId the new doctors employee id
     */
    public void setDoctorEmpId(long doctorEmpId) {
        this.doctorEmpId = doctorEmpId;
    }

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
    public String getPresId() {
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