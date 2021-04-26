package com.example.demo.patient;

import com.example.demo.doctor.Doctor;
import com.example.demo.doctor.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

/**
 * This class acts as an in-between for the patientController and the patientRepository.
 * This is called the "service layer".
 * @author - Justin Rackley
 */
@Service
public final class PatientService {

    //create a permanent reference to the patient and doctor repositories
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    //inject patientRepository and doctorRepository's beans into this class
    @Autowired
    public PatientService(final PatientRepository patientRepository, final DoctorRepository doctorRepository) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    /**
     * a getter for a list of the patients in the database
     * @return a list of all of the patients in the database
     */
    public List<Patient> getPatients(){
        return patientRepository.findAll();
    }

    /**
     * a method to allow a user to add a new patient to the database
     * @param patient teh patient to add to the database
     */
    public void addNewPatient(final Patient patient){
        //make sure this patient doesnt already exist in our database
        if (patientRepository.findPatientBySsn(patient.getSsn()).isPresent()){
            throw new IllegalStateException("A patient with this ssn already exists.");
        }
        patientRepository.save(patient);
    }

    /**
     * a method to allow a user to remove a patient from the database
     * @param ssn the ssn of the patient to remove
     */
    public void removePatient(final long ssn){
        if (patientRepository.findPatientBySsn(ssn).isEmpty()){
            throw new IllegalStateException("The patient with SSN " + ssn + "does not exist.");
        }
        patientRepository.deleteById(ssn);
    }

    /**
     * a method to allow a user to change a patient's name
     * @param ssn the ssn of the patient to change
     * @param firstName the new first name
     * @param lastName the new last name
     */
    public void changeName(@RequestParam final long ssn,
                           @RequestParam(required = false) final String firstName,
                           @RequestParam(required = false) final String lastName)
    {
        //use the helper method to get the patient
        Patient patient = createPatient(ssn);

        //make sure firstName is not null, has length > 0 and is not the same as the current first name
        if (firstName != null && firstName .length()> 0 && !Objects.equals(patient.getFirstName(),firstName)){
            patient.setFirstName(firstName);
        }

        //make sure lastName is not null, has length > 0 and is not the same as the current last name
        if (lastName != null && lastName.length() > 0 && !Objects.equals(patient.getLastName(),lastName)){
            patient.setLastName(lastName);
        }
    }

    /**
     * a method to change a patient's family doctor
     * @param ssn the ssn of the patient to change
     * @param doctorId the employee id of the new family doctor
     */
    public void changeFamilyDoctor(final long ssn, final Long doctorId){
        Patient patient = createPatient(ssn);
        Doctor doctor = createDoctor(doctorId);
        //check if doctor exists in doctor repo
    }

    /**
     * a method to change the phone number of a patient
     * @param ssn the ssn of the patient to change
     * @param newPhone the new phone number
     */
    public void changePhone(final long ssn, final String newPhone){
        Patient patient = createPatient(ssn);

        if (newPhone != null && newPhone.length() == 10 && !(Objects.equals(newPhone,patient.getPhone()))){
            patient.setPhone(newPhone);
        }
    }

    /**
     * a method to change the address of a patient
     * @param ssn the ssn of the patient to change
     * @param newAddress the new address
     */
    public void changeAddress(final long ssn, final String newAddress){
        Patient patient = createPatient(ssn);

        if (newAddress != null && newAddress.length() > 0 && !Objects.equals(patient.getAddress(),newAddress)){
            patient.setAddress(newAddress);
        }
    }

    //A helper method to check if the patient exists in our database
    private Patient createPatient(final long ssn){
        //create a new temporary patient by getting the patient with the given ssn's info from the database
        //else throw an exception
        return patientRepository.findPatientBySsn(ssn).orElseThrow(() -> new IllegalStateException(
                "Patient with SSN " + ssn + " not found."));
    }

    //a helper method to make sure a doctor exists
    private Doctor createDoctor(final long id){
        //create a temporary doctor by getting the doctor with the given id
        //if no such doctor exists, throw an exception
        return doctorRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                "Doctor with id " + id + " not found."));
    }
}
