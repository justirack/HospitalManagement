package com.example.demo.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Service
public class PatientService {

    //create a permanent reference to the repository
    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getPatients(){
        return patientRepository.findAll();
    }

    public void addNewPatient(Patient patient){
        //make sure this patient doesnt already exist in our database
        if (patientRepository.findPatientBySsn(patient.getSsn()).isPresent()){
            throw new IllegalStateException("A patient with this ssn already exists.");
        }
        patientRepository.save(patient);
    }

    public void removePatientBySsn(Long ssn){
        if (patientRepository.findPatientBySsn(ssn).isEmpty()){
            throw new IllegalStateException("The patient with SSN " + ssn + "does not exist.");
        }
    }

    public void changeName(@RequestParam Long ssn,
                           @RequestParam(required = false) String firstName,
                           @RequestParam(required = false) String lastName)
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

    public void changeFamilyDoctor(Long ssn, String newDoctor){
        Patient patient = createPatient(ssn);

        //check if doctor exists in doctor repo
    }

    public void changePhone(Long ssn, Integer newPhone){
        Patient patient = createPatient(ssn);

        if (newPhone != null && newPhone > 0 && newPhone != patient.getPhone()){
            patient.setPhone(newPhone);
        }
    }

    public void changeAddress(Long ssn, String newAddress){
        Patient patient = createPatient(ssn);

        if (newAddress != null && newAddress.length() > 0 && !Objects.equals(patient.getAddress(),newAddress)){
            patient.setAddress(newAddress);
        }
    }

    //A helper method to check if the patient exists in our database
    private Patient createPatient(Long ssn){
        //create a new temporary patient by getting the patient with the given ssn's info from the database
        //else throw an exception
        return patientRepository.findPatientBySsn(ssn).orElseThrow(() -> new IllegalStateException(
                "Patient with SSN " + ssn + " not found."));
    }

//    private Doctor createDoctor (){
//        //make and return new doctor here
//    }
}
