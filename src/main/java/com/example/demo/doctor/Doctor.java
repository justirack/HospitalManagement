/**
 * this class represents doctors in the hospital management system
 * each doctor will have an id, first name, last name and phone
 * @author - Justin Rackley
 */

package com.example.demo.doctor;

import javax.persistence.*;

@Entity
@Table
public class Doctor {

    //A doctors employee id is the primary key
    @Id
    //employee id's are generated in a sequence
    @SequenceGenerator(name = "employee_id",sequenceName = "employee_id",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "employee_id")
    @Column(name = "employee_id", updatable = false)
    private Long empId;
    @Column(name = "first_name",nullable = false)
    private String firstName;
    @Column(name = "last_name",nullable = false)
    private String lastName;
    @Column(name = "phone",nullable = false,length = 10)
    private String phone;

    public Doctor(Long empId, String firstName, String lastName, String phone) {
        this.empId = empId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public Doctor() {
    }

    /**
     * setter for a doctors first name
     * @param firstName the new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * setter for a doctors last name
     * @param lastName the new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * setter for a doctors phone
     * @param phone the new phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * getter for a doctors employee id
     * @return the doctors employee id
     */
    public Long getEmpId() {
        return empId;
    }

    /**
     * getter for a doctors first name
     * @return the doctors first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * getter for a doctors last name
     * @return the doctors last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * getter for a doctor's phone number
     * @return the doctors phone number
     */
    public String getPhone() {
        return phone;
    }
}
