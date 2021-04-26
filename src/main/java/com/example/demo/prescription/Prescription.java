package com.example.demo.prescription;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * This class will represent a prescription in the hospital management system.
 * @author - Justin Rackley
 */
@Entity
@Table
public final class Prescription {

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

    public Prescription(final long presId, final long patientSsn, final long doctorEmpId, final long amount) {
        this.presId = presId;
        this.patientSsn = patientSsn;
        this.doctorEmpId = doctorEmpId;
        this.amount = amount;
    }

    public Prescription() {

    }

    /**
     * setter for the drug amount
     * @param amount the new amount of the drug
     */
    public void setAmount(final long amount) {
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