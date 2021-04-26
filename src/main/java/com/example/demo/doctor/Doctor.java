package com.example.demo.doctor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * This class represents doctors in the hospital management system.
 * Each doctor will have an id, first name, last name and phone.
 * @author - Justin Rackley
 */
@Entity
@Table
public final class Doctor {

    //A doctors employee id is the primary key
    @Id
    //employee id's are generated in a sequence
    @SequenceGenerator(name = "employee_id",sequenceName = "employee_id",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "employee_id")
    @Column(name = "employee_id", updatable = false)
    private long empId;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false,length = 10)
    private String phone;

    public Doctor(final String firstName, final String lastName, final String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public Doctor() {
    }

    /**
     * Setter for a doctors first name.
     * @param firstName The new first name.
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * Setter for a doctors last name.
     * @param lastName The new last name.
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * Setter for a doctors phone.
     * @param phone The new phone number.
     */
    public void setPhone(final String phone) {
        this.phone = phone;
    }

    /**
     * Getter for a doctors employee id.
     * @return The doctors employee id.
     */
    public long getEmpId() {
        return empId;
    }

    /**
     * Getter for a doctors first name.
     * @return The doctors first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter for a doctors last name.
     * @return The doctors last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Getter for a doctor's phone number.
     * @return The doctors phone number.
     */
    public String getPhone() {
        return phone;
    }
}
