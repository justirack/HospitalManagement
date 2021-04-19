/**
 * this class represents patients in the hospital management system
 * @author - Justin Rackley
 */

package com.example.demo.patient;

import com.example.demo.doctor.Doctor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table
public class Patient {

    //A persons ssn is the primary key
    @Id
    //ssn's will be automatically generated
    @SequenceGenerator(name = "ssn", sequenceName = "ssn", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ssn")
    //ssn should have its own column
    @Column(name = "ssn", updatable = false)
    private Long ssn;
    @Column(name = "family_doctor", nullable = false, columnDefinition = "TEXT")
    @Type(type = "Doctor")
    private Doctor familyDoctor;
    @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
    private String firstName;
    @Column(name = "last_name", nullable = false, columnDefinition = "TEXT")
    private String lastName;
    @Column(name = "phone",nullable = false,columnDefinition = "LONG",length = 10)
    private Integer phone;
    @Column(name = "address",nullable = false,columnDefinition = "TEXT")
    private String address;


    public Patient(Doctor familyDoctor, String firstName, String lastName, Integer phone, String address) {
        this.familyDoctor = familyDoctor;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
    }

    public Patient() {
    }

    /**
     * update a patients family doctor
     * @param familyDoctor the patients new family doctor
     */
    public void setFamilyDoctor(Doctor familyDoctor) {
        this.familyDoctor = familyDoctor;
    }

    /**
     * update a patients first name
     * @param firstName the patients new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * update a persons last name
     * @param lastName the persons new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * update a persons phone number
     * @param phone the persons new phone number
     */
    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    /**
     * update a persons address
     * @param address the persons new phone number
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * getter for a patients ssn
     * @return the patients ssn
     */
    public Long getSsn() {
        return ssn;
    }

    /**
     * getter for a patients family doctor
     * @return the patients family doctor
     */
    public Doctor getFamilyDoctor() {
        return familyDoctor;
    }

    /**
     * getter for a patients first name
     * @return the patients first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * getter for a patients last name
     * @return the patients last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * getter for a patients phone number
     * @return the patients phone number
     */
    public Integer getPhone() {
        return phone;
    }

    /**
     * getter for a patients address
     * @return the patients address
     */
    public String getAddress() {
        return address;
    }
}
