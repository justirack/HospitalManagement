package com.example.demo.patient;

import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;

@Entity(name = "patient")
@Table(name = "patient")
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
}
