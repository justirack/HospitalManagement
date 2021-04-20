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
    private String first_name;
    @Column(name = "last_name",nullable = false)
    private String last_name;
    @Column(name = "phone",nullable = false,length = 10)
    private String phone;

    public Doctor(Long empId, String first_name, String last_name, String phone) {
        this.empId = empId;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
    }

    public Doctor() {
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getEmpId() {
        return empId;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getPhone() {
        return phone;
    }
}
