/*
  Copyright 2021, Justin Rackley, All rights reserved.
*/
package com.hospital.manager.prescription;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.hospital.manager.doctor.Doctor;
import com.hospital.manager.exception.CustomException.FailedRequestException;
import com.hospital.manager.patient.Patient;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
@Api(tags = "prescription")
@RequestMapping(path = "prescription")
public final class PrescriptionController {

    /**
     * <p>
     *     Allow a client to get a patient or a list of prescription from the database.
     * </p>
     * @param payload THe payload containing the prescriptions information.
     * @return The prescription or a list of prescriptions.
     */
    @GetMapping(path = "get")
    @ApiOperation("Retrieves a single prescription or a list of prescriptions")
    @ApiResponses({
            @ApiResponse(code = 200, message = "If the prescription(s) were retrieved successfully"),
            @ApiResponse(code = 404, message = "If no prescription(s) correspond to the criteria supplied")
    })
    public List<PrescriptionResponsePayload> get(final RetrievalRequestPayload payload){
        log.info("Attempting to find prescription pertaining to request={}", payload);

        if (payload.getId() == null){
            final List<PrescriptionResponsePayload> results = new ArrayList<>();
            final List<Prescription> prescriptions = service.getPrescriptions();
            log.info("found {} results. Returning them",prescriptions.size());

            for (Prescription prescription : prescriptions) {
                results.add(new PrescriptionResponsePayload(prescription));
            }
            return results;
        }

        Prescription prescription = service.getPrescription(payload.getId());

        if (prescription != null){
            log.info("returning prescription {}", prescription);

            final PrescriptionResponsePayload results = new PrescriptionResponsePayload(prescription);
            return Collections.unmodifiableList(List.of(results));
        }
        throw new FailedRequestException("Unable to load prescription(s). Please make sure all information " +
                "is correct and try again");
    }

    /**
     * <p>
     *     Allow a client to update a prescription information.
     * </p>
     * @param payload the payload containing the prescriptions new information.
     * @return The prescriptions new information.
     */
    @PutMapping(path = "update")
    @ApiOperation("Updates a prescription information")
    public PrescriptionResponsePayload update(final UpdateRequestPayload payload){
        log.info("updating information for prescription {}",payload);

        if (payload.getName() != null){
            log.info("Updating the name of prescription with id {}",payload.getId());
            service.changeName(payload.getId(),payload.getName());
        }
        if (payload.getAmount() != null){
            log.info("Updating the amount of prescription with id {}",payload.getId());
            service.changeAmount(payload.getId(),payload.getAmount());
        }
        Prescription prescription = service.getPrescription(payload.getId());
        return new PrescriptionResponsePayload(prescription);
    }

    /**
     * <p>
     *     Allow a client to add a prescription to the database.
     * </p>
     * @param payload The payload containing the prescriptions information.
     * @return The status of if the prescription was successfully added.
     */
    @PutMapping(path = "add")
    @ApiOperation("Adds a prescription to the database")
    public HttpStatus add (final CreatePrescriptionPayload payload){
        return service.add(payload.getName(),payload.getPatient(),payload.getDoctor(),payload.getAmount());
    }

    /**
     * <p>
     *     Allow a client to delete a prescription from the database.
     * </p>
     * @param payload The payload containing the prescriptions id.
     * @return The status of if the prescription was successfully deleted.
     */
    @DeleteMapping(path = "delete")
    @ApiOperation("Removes a prescription from the database")
    public HttpStatus delete(final  DeleteRequestPayload payload){
        return service.delete(payload.getId());
    }




    @ToString
    @Getter
    @RequiredArgsConstructor
    @ApiModel(description = "The request details supplied when adding a new prescription to the database.")
    private static final class CreatePrescriptionPayload{
        @ApiModelProperty(value = "The prescriptions name.")
        final String name;
        @ApiModelProperty(value = "The prescriptions amount")
        final Long amount;
        @ApiModelProperty("The prescriptions patient.")
        final Patient patient;
        @ApiModelProperty("The prescriptions doctor.")
        final Doctor doctor;
    }

    @ToString
    @Getter
    @RequiredArgsConstructor
    @ApiModel(description = "The request details supplied when retrieving a prescription from the database.")
    private static final class RetrievalRequestPayload{
        @ApiModelProperty(
                value = "The unique, database identifier for the prescription to retrieve" +
                        " If null, return all prescriptions in the database.",
                required = true,
                example = "1024"
        )
        final Long id;
    }

    @ToString
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @RequiredArgsConstructor
    @ApiModel(description = "The request details supplied when updating a prescriptions info.")
    private static final class UpdateRequestPayload{
        @ApiModelProperty(value = "The prescriptions id",required = true)
        final Long id;
        @ApiModelProperty(value = "The prescriptions name.")
        final String name;
        @ApiModelProperty(value = "The prescriptions amount.")
        final Long amount;
    }

    @ToString
    @Getter
    @RequiredArgsConstructor
    @ApiModel(description = "The request details supplied when deleting a prescription from the database.")
    private static final class DeleteRequestPayload{
        @ApiModelProperty(
                value = "The unique, database identifier for the prescription to retrieve" +
                        " If null, return all prescriptions in the database.",
                required = true,
                example = "1024"
        )
        final Long id;
    }

    private static final class PrescriptionResponsePayload{
        public PrescriptionResponsePayload(final Prescription prescription){
            id = prescription.getId();
            name = prescription.getName();
            amount = prescription.getAmount();
            patient = prescription.getPatient();
            doctor = prescription.getDoctor();
        }

        final Long id;
        final String name;
        final Long amount;
        final Patient patient;
        final Doctor doctor;

    }

    private final PrescriptionService service;
}
