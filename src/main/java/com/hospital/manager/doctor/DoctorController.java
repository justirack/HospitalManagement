/*
  Copyright 2021, Justin Rackley, All rights reserved.
*/
package com.hospital.manager.doctor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.hospital.manager.appointment.Appointment;
import com.hospital.manager.exception.CustomException.FailedRequestException;
import com.hospital.manager.exception.CustomException.NotFoundException;
import com.hospital.manager.patient.Patient;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 *     Represents the REST endpoints that provide CRUD functionality for the
 *     underlying {@link Doctor} objects by this service.
 * </p>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "doctor")
@RequestMapping(path = "doctor")
public final class DoctorController {

    /**
     * <p>
     *     Allow the client to get the {@link Doctor} with a given id or a list of all
     *     doctors if no id is supplied.
     * </p>
     *
     * @param payload The payload containing the id of the doctor to return. Return a list of all doctors if null.
     * @return The doctor with given id or the list of all doctors.
     */
    @GetMapping(path = "get")
    @ApiOperation("Retrieves a list of doctors or a single doctor.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "If the doctor(s) were retrieved successfully"),
            @ApiResponse(code = 404, message = "If no doctor(s) correspond to the criteria supplied")
    })
    public List<DoctorResponsePayload> get(final RetrievalRequestPayload payload){
        //FIXME payload.getID() is always null, forces this method to always return a list of all doctors
        log.info("Attempting to find the doctor pertaining to request={}",payload);
        //return all doctors if the client does not supply an id
        if (payload.getId() == null){
            //create a list to add all of the doctors to in the form of DoctorResponsePayload
            final List<DoctorResponsePayload> results = new ArrayList<>();

            //get a list of all doctors from the database
            final List<Doctor> doctors = service.getDoctors();

            //log the number of doctors found
            log.info("Found {} result(s). Returning them",doctors.size());

            //loop and put all doctors into the ResponsePayload list
            for (final Doctor doctor: doctors){
                results.add(new DoctorResponsePayload(doctor));
            }

            //return the responsePayload list
            return Collections.unmodifiableList(results);
        }
        //get the doctor with given id from the database
        final Doctor doctor = service.getDoctor(payload.getId());

        //return the doctor as a single entry in a list
        if (doctor != null){
            final DoctorResponsePayload responsePayload = new DoctorResponsePayload(doctor);

            log.info("returning doctor={}",responsePayload);

            return Collections.unmodifiableList(List.of(responsePayload));
        }
        //Throw an exception if no id is supplied or the supplied id returns nothing.
        throw new NotFoundException("Unable to load doctor(s). Please make sure all information is correct " +
                "and try again");
    }

    /**
     * <p>
     *     Allow a client to update a {@link Doctor} information.
     * </p>
     * @param payload The payload containing the new information.
     * @return The doctors new information.
     */
    @PutMapping(path = "update")
    public DoctorResponsePayload update (final UpdateRequestPayload payload){
        boolean isSuccessful = false;

        //if new information is not null, change it and update isSuccessful
        if (payload.getFirstName() != null){
            service.changeFirstName(payload.getId(),payload.getFirstName());
            isSuccessful = true;
        }
        if (payload.getLastName() != null){
            service.changeLastName(payload.getId(),payload.getLastName());
            isSuccessful = true;
        }
        if (payload.getPhone() != null){
            service.changePhone(payload.getId(),payload.getPhone());
            isSuccessful = true;
        }

        //return the patients new information
        if (isSuccessful){
            Doctor doctor = service.getDoctor(payload.getId());
            return new DoctorResponsePayload(doctor);
        }
        //Throw exception if nothing was updated
        throw new FailedRequestException("All provided information is the same as current information on file. " +
                "Please provide new information and try again");
    }

    /**
     * <p>
     *     Allow a client to add a {@link Doctor} to the database.
     * </p>
     *
     * @param payload The payload containing the new doctors information.
     * @return The status indicating if the doctor was successfully added to the database.
     */
    @PostMapping(path = "add")
    public HttpStatus hire(final CreateRequestPayload payload){
        return service.hire(payload.firstName, payload.lastName, payload.phone);
    }

    /**
     * <p>
     *     Allow a client to delete a {@link Doctor} from the database.
     * </p>
     *
     * @param payload the payload containing the id of the doctor to delete from the database.
     * @return The status indicating if the doctor was removed successfully.
     */
    @DeleteMapping(path = "delete")
    public HttpStatus deleteDoctor(final DeleteRequestPayload payload){
        return service.remove(payload.getId());
    }



    /**
     * <p>
     *     Represents the payload that will be received from the client.
     *     when a new {@link Doctor} is to be hired.
     * </p>
     */
    @ToString
    @Getter
    @RequiredArgsConstructor
    @ApiModel(description = "The request details supplied when hiring a new doctor.")
    private static final class CreateRequestPayload{
        private final String firstName;
        private final String lastName;
        @ApiModelProperty(value = "Must be 10 digits.")
        private final String phone;
    }

    /**
     * <p>
     *     Represents a payload that will be received from the client.
     *     when a {@link Doctor} is to be deleted from the database.
     * </p>
     */
    @ToString
    @Getter
    @RequiredArgsConstructor
    @ApiModel(description = "The request details supplied when deleting a new doctor.")
    private static final class DeleteRequestPayload{
        @ApiModelProperty(
                value = "The unique, database identifier for the doctor to retrieve" +
                        " If null, return all doctors in the database",
                required = true,
                example = "1024")
        private final Long id;
    }

    /**
     * <p>
     *     Represents the payload that will be received from the client.
     *     when a {@link Doctor}'s information is requested.
     * </p>
     */
    @Getter
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    @NoArgsConstructor
    @ApiModel(description = "The request details supplied when retrieving a doctor's details.")
    private static final class RetrievalRequestPayload{
        @ApiModelProperty(
                value = "The unique, database identifier for the doctor to retrieve" +
                        " If null, return all doctors in the database",
                example = "1024")
        private Long id;
    }

    /**
     * <p>
     *     Represents the payload that will be received from the client.
     *     when a {@link Doctor}'s information is to be updated.
     * </p>
     */
    @ToString
    @Getter
    @RequiredArgsConstructor
    @ApiModel(description = "The request details received when attempting to update a doctor's information")
    private static final class UpdateRequestPayload{
        private final long id;
        private final String firstName;
        private final String lastName;
        @ApiModelProperty(value = "Must be 10 digits.")
        private final String phone;
    }

    /**
     * <p>
     *     Represents the payload that will be returned to the client.
     *     when they request a {@link Doctor}'s information.
     * </p>
     */
    @ToString
    @Getter
    private static final class DoctorResponsePayload{
        private DoctorResponsePayload(final Doctor doctor){
            id = doctor.getId();
            firstName = doctor.getFirstName();
            lastName = doctor.getLastName();
            phone = doctor.getPhone();
            appointments = doctor.getAppointments();
            patients = doctor.getPatients();

        }
        private final Long id;
        private final String firstName;
        private final String lastName;
        private final String phone;
        private final List<Appointment> appointments;
        private final List<Patient> patients;
    }

    private final DoctorService service;
}