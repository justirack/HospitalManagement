package com.example.demo.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Service
public class DoctorService {

    //create a permanent reference to the doctor repository
    private final DoctorRepository doctorRepository;

    @Autowired

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> getDoctors(){
        return doctorRepository.findAll();
    }

    public void addDoctor(Doctor doctor){
        if (doctorRepository.findById(doctor.getEmpId()).isPresent()){
            throw new IllegalStateException("A doctor with this employee id already exists.");
        }
        doctorRepository.save(doctor);
    }

    public void removeDoctor(Long id){
        if (doctorRepository.findById(id).isEmpty()){
            throw new IllegalStateException("No doctor with this employee id found.");
        }
        doctorRepository.deleteById(id);
    }

    public void changeName(@RequestParam Long id,
                           @RequestParam(required = false) String firstName,
                           @RequestParam(required = false) String lastName) {
        Doctor doctor = createDoctor(id);

        if (firstName != null && firstName.length() > 0 && !Objects.equals(doctor.getFirstName(),firstName)){
            doctor.setFirstName(firstName);
        }

        if (lastName != null && lastName.length() > 0 && !Objects.equals(doctor.getFirstName(),firstName));
    }

    public void changePhone(Long id, String phone){
        Doctor doctor = createDoctor(id);

        if (phone != null && phone.length() == 10 && !Objects.equals(doctor.getPhone(),phone)){
            doctor.setPhone(phone);
        }
    }

    //a helper method to make sure a doctor exists
    private Doctor createDoctor(Long id){
        //create a temporary doctor by getting the doctor with the given id
        //if no such doctor exists, throw an exception
        return doctorRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                "Doctor with id " + id + " not found."));
    }
}
