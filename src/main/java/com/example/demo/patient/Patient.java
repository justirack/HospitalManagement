package com.example.demo.patient;

import com.example.demo.doctor.Doctor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * This class represents patients in the hospital management system.
 * Each patient will have a ssn, family doctor, first name, last name, phone and address.
 * @author - Justin Rackley
 */
@Entity
@Table
public final class Patient {

    //A persons ssn is the primary key
    @Id
    //ssn's will be automatically generated
    @SequenceGenerator(name = "ssn", sequenceName = "ssn", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ssn")
    @Column(name = "ssn",updatable = false)
    private long ssn;
    private long familyDoctorId;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;

    public Patient(final long familyDoctorId, final String firstName, final String lastName,
                   final String phone, final String address) {
        this.familyDoctorId = familyDoctorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
    }

    public Patient() {
    }

    /**
     * Setter for a patients family doctor.
     * @param familyDoctorId The patients new family doctor.
     */
    public void setFamilyDoctor(final long familyDoctorId) {
        this.familyDoctorId = familyDoctorId;
    }

    /**
     * Setter for a patients first name.
     * @param firstName The patients new first name.
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * Setter for a persons last name.
     * @param lastName The persons new last name.
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * Setter for a persons phone number.
     * @param phone The persons new phone number.
     */
    public void setPhone(final String phone) {
        this.phone = phone;
    }

    /**
     * Setter for a persons address.
     * @param address The persons new phone number.
     */
    public void setAddress(final String address) {
        this.address = address;
    }

    /**
     * Getter for a patients ssn.
     * @return the patients ssn.
     */
    public long getSsn() {
        return ssn;
    }

    /**
     * Getter for a patients family doctor.
     * @return The patients family doctor.
     */
    public long getFamilyDoctorId() {
        return familyDoctorId;
    }

    /**
     * Getter for a patients first name.
     * @return The patients first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter for a patients last name.
     * @return The patients last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Getter for a patients phone number.
     * @return The patients phone number.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Getter for a patients address.
     * @return The patients address.
     */
    public String getAddress() {
        return address;
    }
}
