/*
  Copyright 2021, Justin Rackley, All rights reserved.
*/
package com.hospital.manager.doctor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.hospital.manager.appointment.Appointment;
import com.hospital.manager.exception.CustomException.FailedRequestException;
import com.hospital.manager.patient.Patient;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    @GetMapping
    @ApiOperation("Retrieves a list of doctors or a single doctor.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "If the doctor(s) were retrieved successfully"),
            @ApiResponse(code = 404, message = "If no doctor(s) correspond to the criteria supplied")
    })
    public List<DoctorResponsePayload> get(final RetrievalRequestPayload payload){
        log.info("Attempting to find the doctor pertaining to request={}",payload);

        //return all doctors if the client does not supply an id
        if (Objects.isNull(payload.getId())){
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
        final Doctor doctor = service.getDoctor(payload.id);

        //return the doctor as a single entry in a list
        if (doctor != null){
            final DoctorResponsePayload responsePayload = new DoctorResponsePayload(doctor);

            log.info("returning appointment={}",responsePayload);

            return Collections.unmodifiableList(List.of(responsePayload));
        }
        throw new FailedRequestException("Request Failed. The list of doctors or requested doctor could not be found.");
    }

    /**
     * Allow a user to add a {@link Doctor} to the database.
     * @param firstName The doctors first name.
     * @param lastName The doctors last name.
     * @param phone The doctors phone number.
     */
    @PostMapping(path = "add")
    public HttpStatus hire(final CreateRequestPayload payload){
        return service.hire(payload.firstName, payload.firstName, payload.phone);
    }

    /**
     * Allow a user to delete a {@link Doctor} from the repository.
     * @param doctorId The id of the doctor to remove.
     */
    @DeleteMapping(path = "delete/{doctorId}")
    public HttpStatus deleteDoctor(@PathVariable final long doctorId){
        return service.remove(doctorId);
    }

    /**
     * Allow a {@link Doctor} to change their first name.
     * @param id The doctors id.
     * @param firstName The doctors new first name.
     */
    @PutMapping(path = "changeFirstName/{id}/{firstName}")
    public HttpStatus changeFirstName(@PathVariable final long id, @PathVariable final String firstName){
        return service.changeFirstName(id,firstName);
    }

    /**
     * Allow a {@link Doctor} to change their last name.
     * @param id The doctors id.
     * @param lastName The doctors new last name.
     */
    @PutMapping(path = "changeLastName/{id}/{lastName}")
    public HttpStatus changeLastName(@PathVariable final long id, @PathVariable final String lastName){
         return service.changeLastName(id,lastName);
    }

    /**
     * Allow a {@link Doctor} to change their phone number.
     * @param id The doctors id.
     * @param phone The doctors new phone number.
     */
    @PutMapping(path = "changePhone/{id}/{phone}")
    public HttpStatus changePhone(@PathVariable final long id, @PathVariable final String phone){
        return service.changePhone(id,phone);
    }


    /**
     * <p>
     *     Represents the payload that will be received from the client
     *     when a new {@link Doctor} is to be hired.
     * </p>
     */
    @ToString
    @Getter(AccessLevel.PACKAGE)
    @RequiredArgsConstructor(access = AccessLevel.PACKAGE)
    @ApiModel(description = "The request details supplied when hiring a new doctor.")
    public static final class CreateRequestPayload{
        private final String firstName;
        private final String lastName;
        private final String phone;
        private final List<Appointment> appointments;
        private final List<Patient> patients;
    }

    /**
     * <p>
     *     Represents the payload that will be received from the client
     *     when a {@link Doctor}'s information is requested.
     * </p>
     */
    @Getter
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    @ApiModel(description = "The request details supplied when retrieving a doctor's details.")
    public static final class RetrievalRequestPayload{
        @ApiModelProperty(
                value = "The unique, database identifier for the doctor to retrieve" +
                        " If null, return all doctors in the database",
                required = false,
                example = "1024",
                position = 0)
        private long id;
    }

    /**
     * <p>
     *     Represents the payload that will be received from the client
     *     when a {@link Doctor}'s information is to be updated.
     * </p>
     */
    @ToString
    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PACKAGE)
    public static final class UpdateRequestResponsePayload{
        private final String firstName;
        private final String lastName;
        private final String phone;
        private final List<Appointment> appointments;
        private final List<Patient> patients;
    }

    /**
     * <p>
     *     Represents the payload that will be returned to the client
     *     when they request a {@link Doctor}'s information.
     * </p>
     */
    @ToString
    @Getter(AccessLevel.PACKAGE)
    public static final class DoctorResponsePayload{
        private DoctorResponsePayload(final Doctor doctor){
            firstName = doctor.getFirstName();
            lastName = doctor.getLastName();
            phone = doctor.getPhone();
            appointments = doctor.getAppointments();
            patients = doctor.getPatients();

        }
        private final String firstName;
        private final String lastName;
        private final String phone;
        private final List<Appointment> appointments;
        private final List<Patient> patients;
    }

    private final DoctorService service;
}