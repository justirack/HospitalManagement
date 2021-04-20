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
    @Column(name = "employee_id")
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

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getEmpId() {
        return empId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }
}
