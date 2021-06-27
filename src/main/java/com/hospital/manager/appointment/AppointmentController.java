/*
  Copyright 2021, Justin Rackley, All rights reserved.
*/
package com.hospital.manager.appointment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
    /**
     * Allow a user to get a list of all appointments.
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
        return Collections.emptyList(); 
    }

    /**
     * Allow a user to book an appointment.
     *
     * @param patientSsn The ssn of the patient booking the appointment.
     * @param doctorEmpId The empId of the doctor the appointment is being booked with.
     * @param date The date of the appointment.
     * @param room The room the appointment is in.
     */
    @PostMapping
    public HttpStatus create(final CreateRequestPayload payload)
    {
        return service.book(
            payload.getSsn(),
            payload.getDoctorId(),
            // FIXME - The incoming payload date string needs to be converted into
            //         its equivalent Date object here.
            new Date(),
            payload.getRoom());
    }

    /**
     * Allow a user to change the date of an appointment.
     * @param appId The id of the appointment.
     * @param date The new date of the appointment.
     */
    @PutMapping("changeDate/{appId}/{date}")
    public HttpStatus changeDate(@PathVariable final long appId, @PathVariable final Date date){
        return service.changeDate(appId, date);
    }

    /**
     * Allow a user to cancel an appointment.
     * @param appId The id of the appointment to cancel.
     */
    @DeleteMapping(path = "cancel/{appId}")
    public HttpStatus cancel(@PathVariable final long appId){
        return service.cancel(appId);
    }

    /**
     * Allow a user to change the room of an appointment.
     * @param appId The id of the appointment.
     * @param room The new room of the appointment.
     */
    @PutMapping("changeRoom/{appId}/{room}")
    public HttpStatus changeRoom(@PathVariable final long appId, @PathVariable final int room){
        return service.changeRoom(appId, room);
    }

    @Getter
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    @ApiModel(
        description = "The request details supplied when retrieving existing appointment details.")
    public static final class RetrievalRequestPayload
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
    @Getter(AccessLevel.PACKAGE)
    @RequiredArgsConstructor(access = AccessLevel.PACKAGE)
    @ApiModel(
        description = "The request details supplied when creating (i.e. booking) a new appointment.")
    public static final class CreateRequestPayload
    {
        private final long ssn;
        private final long doctorId;
        private final String date;
        private final int room;
    }

    @ToString
    @Getter(AccessLevel.PACKAGE)
    @RequiredArgsConstructor(access = AccessLevel.PACKAGE)
    public static final class UpdateRequestPayload
    {
        private final long id;
        private final long ssn;
        private final long doctorId;
        private final String date;
        private final int room;
    }

    @ToString
    @Getter(AccessLevel.PACKAGE)
    public static final class AppointmentResponsePayload
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

    private final AppointmentService service;
}