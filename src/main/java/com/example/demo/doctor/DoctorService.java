/**
 * this class acts as an in-between for the doctorController and the doctorRepository
 * this is called the "service layer"
 * @author - Justin Rackley
 */

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

    //inject the doctorRepository bean into this class' bean
    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    /**
     * getter for a list of all the doctors in the database
     * @return a list of all the doctors
     */
    public List<Doctor> getDoctors(){
        return doctorRepository.findAll();
    }

    /**
     * a method to add a new doctor to the database
     * @param doctor the doctor to add to the databse
     */
    public void addDoctor(Doctor doctor){
        if (doctorRepository.findById(doctor.getEmpId()).isPresent()){
            throw new IllegalStateException("A doctor with this employee id already exists.");
        }
        doctorRepository.save(doctor);
    }

    /**
     * a method to remove a doctor from the database
     * @param id the employee id of the doctor to remove
     */
    public void removeDoctor(long id){
        if (doctorRepository.findById(id).isEmpty()){
            throw new IllegalStateException("No doctor with this employee id found.");
        }
        doctorRepository.deleteById(id);
    }

    /**
     * a method to change the name of a doctor
     * @param id the id of the doctor to change
     * @param firstName the doctors new first name
     * @param lastName the doctors new last name
     */
    public void changeName(@RequestParam long id,
                           @RequestParam(required = false) String firstName,
                           @RequestParam(required = false) String lastName) {
        Doctor doctor = createDoctor(id);

        if (firstName != null && firstName.length() > 0 && !Objects.equals(doctor.getFirstName(),firstName)){
            doctor.setFirstName(firstName);
        }

        if (lastName != null && lastName.length() > 0 && !Objects.equals(doctor.getFirstName(),firstName));
    }

    /**
     * a method to change a doctors phone number
     * @param id the id of the doctor to change
     * @param phone the doctors new phone number
     */
    public void changePhone(long id, String phone){
        Doctor doctor = createDoctor(id);

        if (phone != null && phone.length() == 10 && !Objects.equals(doctor.getPhone(),phone)){
            doctor.setPhone(phone);
        }
    }

    //a helper method to make sure a doctor exists
    private Doctor createDoctor(long id){
        //create a temporary doctor by getting the doctor with the given id
        //if no such doctor exists, throw an exception
        return doctorRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                "Doctor with id " + id + " not found."));
    }
}
