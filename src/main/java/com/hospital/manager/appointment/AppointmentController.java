/*
  Copyright 2021, Justin Rackley, All rights reserved.
*/
package com.hospital.manager.appointment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.hospital.manager.exception.CustomException.NotFoundException;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * Represents the REST endpoints that provide CRUD functionality for the underlying
 * {@link Appointment} objects managed by this service.
 * </p>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "appointment")
@RequestMapping(path = "appointment")
public final class AppointmentController
{
    // --------------------------------------------------------------------
    // :: Public Interface
    /**
     * <p>
     *     Allow a client to get a list of all {@link Appointment}.
     * </p>>
     * @return The list of all appointments.
     */
    @GetMapping
    @ApiOperation("Retrieves a list of appointments.")
    @ApiResponses({
        @ApiResponse(
            code = 200,
            message = "If the appointments were successfully retrieved."),
        @ApiResponse(
            code = 404,
            message = "If no appointments corresponded to the request criteria supplied.")
    })
    public List<AppointmentResponsePayload> get(final RetrievalRequestPayload payload)
    {
        log.info("Attempting to retrieve the appointment data pertaining to request={}.",
            payload);

        // If the client does not supply a specific appointment id, then return all
        // the appointments we have.
        // TODO: Pagination will be required once the appointment data reaches a
        //       certain volume.
        if(payload.getId() == null)
        {
            final List<AppointmentResponsePayload> results = new ArrayList<>();

            // Retrieve all known appointments to return to the client.
            final List<Appointment> appointments = service.getAppointments();

            log.info("Found {} result(s). Returning them.", appointments.size());

            for(final Appointment currentAppointment : appointments)
            {
                results.add(new AppointmentResponsePayload(currentAppointment));
            }

            return Collections.unmodifiableList(results);
        }

        // Retrieve the specific appointment using the supplied appointment id.
        final Appointment appointment = service.getAppointment(payload.getId());

        // If that specific appointment is found, then return it as the single entry
        // in a list.
        if(appointment != null)
        {
            final AppointmentResponsePayload responsePayload
                = new AppointmentResponsePayload(appointment);

            log.info("Returning appointment={}.", responsePayload);

            return Collections.unmodifiableList(List.of(responsePayload));
        }

        // No appointment was found, return an empty list for now.
        // TODO: we should return a 404 Not Found in this case.
        throw new NotFoundException("Unable to load appointment(s). Please make sure all information is correct " +
                "and try again");
    }

    /**
     * <p>
     *     Allow a client to update an {@link Appointment} room or date.
     * </p>
     * @param payload The payload containing the new information.
     * @return The appointments new information.
     */
    @PutMapping
    public AppointmentResponsePayload update(final UpdateRequestPayload payload) throws Exception{

        //if new information is not null, change it and update isSuccessful
        if (payload.getDate() != null){
            service.changeDate(payload.getId(),FORMATTER.parse(payload.getDate()));
        }
        if (payload.getRoom() != null){
            service.changeRoom(payload.getId(),payload.getRoom());
        }

        //return the patients new information
        Appointment appointment = service.getAppointment(payload.getId());
        return new AppointmentResponsePayload(appointment);

    }

    /**
     * <p>
     *     Allow a client to book an {@link Appointment}.
     * </p>
     * @param payload The payload containing the {@link com.hospital.manager.patient.Patient} and
     *                {@link com.hospital.manager.doctor.Doctor} information for the new appointment.
     * @return The status code indicating if the appointment was successfully booked.
     */
    @PostMapping
    public HttpStatus create(final CreateRequestPayload payload) throws Exception
    {
        return service.book(
            payload.getSsn(),
            payload.getDoctorId(),
            FORMATTER.parse(payload.getDate()),
            payload.getRoom());
    }

    /**
     * <p>
     *     Allow a client to cancel an {@link Appointment}.
     * </p>
     * @param payload The payload containing the id of the appointment to cancel.
     * @return The status of if the appointment was canceled successfully.
     */
    @DeleteMapping(path = "cancel")
    public HttpStatus cancel(final DeleteRequestPayload payload){
        return service.cancel(payload.id);
    }

    // --------------------------------------------------------------------
    // :: Private Nested Classes

    @Getter
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    @NoArgsConstructor
    @ApiModel(
        description = "The request details supplied when retrieving existing appointment details.")
    private static final class RetrievalRequestPayload
    {
        @ApiModelProperty(
            value = "The unique, database identifier for the appointment to retrieve. "
                + "If null, then all appointments within the database are returned.",
            required = false,
            example = "1024",
            position = 0)
        private Long id;
    }

    @ToString
    @Getter
    @RequiredArgsConstructor
    @ApiModel(
        description = "The request details supplied when creating (i.e. booking) a new appointment.")
    private static final class CreateRequestPayload
    {
        @ApiModelProperty(value = "The booking patients ssn")
        private final long ssn;
        @ApiModelProperty(value = "The attending doctors id")
        private final long doctorId;
        @ApiModelProperty(value = "The appointments date. Please enter in the form yyyy-MM-dd hh:mm")
        private final String date;
        @ApiModelProperty(value = "The room for the appointment")
        private final int room;
    }

    @Getter
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    @NoArgsConstructor
    @ApiModel(description = "The request details supplied when deleting an appointment")
    private static final class DeleteRequestPayload{
        @ApiModelProperty(
                value = "The unique, database identifier for the appointment to delete. This cannot be null",
                required = true,
                example = "1024")
        private Long id;

    }

    @ToString
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @RequiredArgsConstructor
    @ApiModel(description = "Update an appointment")
    private static final class UpdateRequestPayload
    {
        @ApiModelProperty(value = "The appointments id.", required = true)
        private final long id;
        @ApiModelProperty(value = "The new patients ssn.")
        private final long ssn;
        @ApiModelProperty(value = "The new doctors id.")
        private final long doctorId;
        @ApiModelProperty(value = "The appointments new date.")
        private final String date;
        @ApiModelProperty(value = "The appointments new room.")
        private final Integer room;
    }

    @ToString
    @Getter
    private static final class AppointmentResponsePayload
    {
        private AppointmentResponsePayload(final Appointment appointment)
        {
            id = appointment.getId();
            patientId = appointment.getPatient() != null
                ? appointment.getPatient().getId() : null;
            doctorId = appointment.getDoctor() != null
                ? appointment.getDoctor().getId() : null;
            room = appointment.getRoom();
            date = appointment.getFormattedDate();
        }

        private final long id;
        private final Long patientId;
        private final Long doctorId;
        private final int room;
        private final String date;
    }

    // --------------------------------------------------------------------
    // :: Private Members
    private final AppointmentService service;

    // --------------------------------------------------------------------
    // :: Private Interface

    // A date formatter used to produce string representations for the date objects
    // contained within this class. This formatter captures the date and time (
    // to the minute only). This does not take into account timezones.
    private static final SimpleDateFormat FORMATTER
            = new SimpleDateFormat("yyyy-MM-dd hh:mm");
}