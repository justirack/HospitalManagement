package com.example.demo.patient;

import javax.persistence.*;

@Entity(name = "patient")
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
    private String familyDoctor;
    @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
    private String firstName;
    @Column(name = "last_name", nullable = false, columnDefinition = "TEXT")
    private String lastName;
    @Column(name = "phone",nullable = false,columnDefinition = "LONG",length = 10)
    private Long phone;
    @Column(name = "address",nullable = false,columnDefinition = "TEXT")
    private String address;

    public Patient(Long ssn, String familyDoctor, String firstName, String lastName, Long phone, String address) {
        this.ssn = ssn;
        this.familyDoctor = familyDoctor;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
    }

    public Patient() {
    }


    public void setFamilyDoctor(String familyDoctor) {
        this.familyDoctor = familyDoctor;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getSsn() {
        return ssn;
    }

    public String getFamilyDoctor() {
        return familyDoctor;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Long getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }
}
