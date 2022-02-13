/*
  Copyright 2021, Justin Rackley, All rights reserved.
*/
package com.hospital.manager.patient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hospital.manager.appointment.Appointment;
import com.hospital.manager.doctor.Doctor;

import com.hospital.manager.exception.CustomException.FailedRequestException;
import com.hospital.manager.exception.CustomException.NotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 *     Represents the REST endpoints that provide CRUD functionality for the
 *     underlying {@link Patient} objects by this service.
 * </p>
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@Api(tags = "patient")
@RequestMapping(path = "patient")
public final class PatientController {

    // --------------------------------------------------------------------
    // :: Public Interface
    /**
     * Allow a client to get an {@link Patient} or a list of patients.
     * @param payload The payload containing the information about the patient to return.
     * @return The patient(s).
     */
    @GetMapping(path = "get")
    @ApiOperation("Retrieves a single patient or a list of patients")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The patient(s) were retrieved successfully"),
            @ApiResponse(code = 404,message = "If no patient(s) correspond to the criteria supplied")
    })
    public List<PatientResponsePayload> get (RetrievalRequestPayload payload){
        log.info("Attempting to find patient pertaining to request={}",payload.getId());

        //if no id is supplied return a list of all patients
        if (payload.getId() == null){
            final List<PatientResponsePayload> results = new ArrayList<>();
            final List<Patient> patients = service.getPatients();

            log.info("Found {} results, returning them",patients.size());

            //add the patients to a list as PatientResponsePayloads
            for (Patient patient:patients) {
                results.add(new PatientResponsePayload(patient));
            }
            return results;
        }
        //get the patient with supplied id and return as item in a list of PatientResponsePayload
        final Patient patient = service.getPatient(payload.getId());

        if (patient != null){
            log.info("returning patient={}",patient);

            final PatientResponsePayload results = new PatientResponsePayload(patient);
            return Collections.unmodifiableList(List.of(results));
        }
        //throw exception if id supplied does not correspond to an existing patient
        throw new NotFoundException("Unable to load patient(s). Please make sure all information is correct " +
                "and try again");
    }

    /**
     * Allow the client to update a {@link Patient} information.
     * @param payload The information about the patient to update.
     * @return The patients new information.
     */
    @PutMapping(path = "update")
    public PatientResponsePayload update (final UpdateRequestPayload payload){

        //if new information is not null, change it and update isSuccessful
        if (payload.getFirstName() != null){
            service.changeFirstName(payload.getId(), payload.getFirstName());
        }
        if (payload.getLastName() != null){
            service.changeLastName(payload.getId(), payload.getLastName());
        }
        if (payload.getPhone() != null){
            service.changePhone(payload.getId(), payload.getPhone());
        }
        if (payload.getAddress() != null){
            service.changeAddress(payload.getId(), payload.getAddress());
        }
        if (payload.getDoctorId() != null) {
            service.changeFamilyDoctor(payload.getId(), payload.getDoctorId());
        }

        //return the patients information
        Patient patient = service.getPatient(payload.getId());
        return new PatientResponsePayload(patient);

    }

    /**
     * Allow a client to add a new {@link Patient} to the database.
     * @param payload The payload containing the new patient's information.
     * @return The status of if the patient was successfully added.
     */
    @PostMapping(path = "add")
    public HttpStatus add(final CreateRequestPayload payload){
        return service.add(
                payload.getDocId(),
                payload.getFirstName(),
                payload.getLastName(),
                payload.getPhone(),
                payload.getAddress()
        );
    }

    /**
     * Allow a client to remove a {@link Patient} from the database.
     * @param payload The payload containing the id of the patient to delete.
     * @return The status of if the patient was sucessfully removed
     */
    @DeleteMapping(path = "delete")
    public HttpStatus remove(final DeleteRequestPayload payload){
        return service.remove(payload.getId());
    }


    // --------------------------------------------------------------------
    // :: Private Nested Classes
    @ToString
    @Getter
    @RequiredArgsConstructor
    @ApiModel(description = "The request details supplied when adding a new patient to the database.")
    private static final class CreateRequestPayload{
        @ApiModelProperty(value = "The new patients first name.")
        private final String firstName;
        @ApiModelProperty(value = "The new patients last name.")
        private final String lastName;
        @ApiModelProperty(value = "The new patients phone number. Must be 10 digits.")
        private final String phone;
        @ApiModelProperty(value = "The new patients address.")
        private final String address;
        @ApiModelProperty(value = "The new patient's doctor's id.")
        private final Long docId;
    }

    @ToString
    @Getter
    @RequiredArgsConstructor
    @ApiModel(description = "The request details supplied when retrieving a patient from the database.")
    private static final class RetrievalRequestPayload{
        @ApiModelProperty(
                value = "The unique, database identifier for the patient to retrieve" +
                        " If null, return all patients in the database.",
                required = true,
                example = "1024")
        private final Long id;
    }

    @ToString
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @RequiredArgsConstructor
    @ApiModel(description = "The request details supplied when updating a patients information.")
    private static final class UpdateRequestPayload{
        @ApiModelProperty(value = "The patient's id",required = true)
        final Long id;
        @ApiModelProperty(value = "The patient's new first name.")
        final String firstName;
        @ApiModelProperty(value = "The patient's new last name.")
        final String lastName;
        @ApiModelProperty(value = "The patient's new phone number. Must be 10 digits.")
        final String phone;
        @ApiModelProperty(value = "The patient's new address.")
        final String address;
        @ApiModelProperty(value = "The patient's new doctor's id.")
        final Long doctorId;
    }

    @ToString
    @Getter
    @RequiredArgsConstructor
    @ApiModel(description = "The request details supplied when deleting a patient from the database.")
    private static final class DeleteRequestPayload{
        @ApiModelProperty(
                value = "The unique, database identifier for the patient to retrieve" +
                        " If null, return all patients in the database",
                required = true,
                example = "1024")
        private final Long id;
    }

    @ToString
    @Getter
    private static final class PatientResponsePayload{
        public PatientResponsePayload(final Patient patient) {
            id = patient.getId();
            firstName = patient.getFirstName();
            lastName = patient.getLastName();
            phone = patient.getPhone();
            address = patient.getAddress();
            doctor = patient.getDoctor();
            appointments = patient.getAppointments();
        }

        private final Long id;
        private final String firstName;
        private final String lastName;
        private final String phone;
        private final String address;
        private final Doctor doctor;
        private final List<Appointment> appointments;
    }

    // --------------------------------------------------------------------
    // :: Private Members
    private final PatientService service;

}
