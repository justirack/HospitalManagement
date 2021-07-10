/*
  Copyright 2021, Justin Rackley, All rights reserved.
*/
package com.hospital.manager.drug;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 *     Represents the REST endpoints that provide CRUD functionality for the
 *     underlying {@link Drug} objects by this service.
 * </p>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "drug")
@RequestMapping("drug")
public final class DrugController {

    /**
     * <p>
     *     Allow a client to get a {@link Drug} or list of drugs.
     * </p>
     * @param payload The payload containing the information about the drug to return.
     * @return The drug(s).
     */
    @GetMapping(path = "get")
    @ApiOperation("Retrieves a single drug or a list of drugs.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "If the drug(s) were retrieved successfully"),
            @ApiResponse(code = 404, message = "If no drug(s) correspond ot the criteria supplied")
})
     public List<DrugResponsePayload> get(final RetrievalRequestPayload payload){
        log.info("Attempting to find the drug pertaining to request={}",payload);

        if (payload.getId() == null){
            //create a list to add DrugResponsePayloads and a list of all drugs
            final List<DrugResponsePayload> responsePayload = new ArrayList<>();
            final List<Drug> drugs = service.getDrugs();

            log.info("Found {} result(s). returning them",drugs.size());

            //loop the elements from drugs into responsePayloas
            for (final Drug drug : drugs) {
                responsePayload.add(new DrugResponsePayload(drug));
            }
            //return the list of DrugResponsePayload
            return responsePayload;
        }

        final Drug drug = service.getDrug(payload.getId());

        //if the drug exists return it as an element of a ResponsePayload List
        if (drug != null){
            final DrugResponsePayload result = new DrugResponsePayload(drug);
            log.info("Returning doctor={}",result);
            return Collections.unmodifiableList(List.of(result));
        }
        //throw an exception if the id supplied doesnt correspond to a drug
        throw new NotFoundException("Unable to load drug(s). Please make sure all information is correct " +
                "and try again");
     }

    /**
     * <p>
     *     Allow the client to update a {@link Drug} information.
     * </p>
     * @param payload The payload containing the drugs information.
     * @return The drugs new information
     */
     @PutMapping(path = "update")
     public DrugResponsePayload update(final UpdateRequestPayload payload){
         //if new information is not null, update it
         if (payload.getName() != null){
            service.changeName(payload.getId(),payload.getName());
         }
         if (payload.getDescription() != null){
            service.changeDescription(payload.getId(),payload.getDescription());
         }
         if (payload.getFormula() != null){
            service.changeFormula(payload.getId(),payload.getFormula());
         }
         //return the patients new information
         return new DrugResponsePayload(service.getDrug(payload.getId()));
     }

    /**
     * <p>
     *     Allow a client to add a {@link Drug} to the database.
     * </p>
     * @param payload The new drugs information.
     * @return The status of if the drug was successfully added.
     */
     @PostMapping(path = "add")
     public HttpStatus add(final CreateRequestPayload payload){
        return service.add(payload.getFormula(), payload.getName(), payload.getDescription());
     }

    /**
     * <p>
     *     Allow a client to delete a {@link Drug} from the database.
     * </p>
     * @param payload The drug to deletes information.
     * @return The status of if the drug was successfully deleted.
     */
     @DeleteMapping(path = "delete")
     public HttpStatus delete(final DeleteRequestPayload payload){
        return service.delete(payload.getId());
     }


    @ToString
    @Getter
    @RequiredArgsConstructor
    @ApiModel(description = "The request details supplied when hiring a new drug.")
    private static final class CreateRequestPayload{
        @ApiModelProperty(value = "The drugs name.")
        private final String name;
        @ApiModelProperty(value = "The drugs description.")
        private final String description;
        @ApiModelProperty(value = "The drugs formula.")
        private final String formula;
    }

    @Getter
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    @NoArgsConstructor
    @ApiModel(description = "The request details supplied when retrieving a drug's details.")
    private static final class RetrievalRequestPayload{
        @ApiModelProperty(value = "The unique, database identifier for the drug to retrieve. " +
                "If null, return all doctors in the database.",example = "1024")
        private Long id;
    }

    @ToString
    @Getter
    @RequiredArgsConstructor
    @ApiModel(description = "The request details received when attempting to update a drug's information")
    private static final class UpdateRequestPayload {
        @ApiModelProperty(value = "The drugs id.",required = true)
        private final Long id;
        @ApiModelProperty(value = "The drugs name.")
        private final String name;
        @ApiModelProperty(value = "The drugs description.")
        private final String description;
        @ApiModelProperty(value = "The drugs formula.")
        private final String formula;
    }

    @ToString
    @Getter
    @RequiredArgsConstructor
    @ApiModel(description = "The request details supplied when deleting a new drug.")
    public static final class DeleteRequestPayload{
        @ApiModelProperty(value = "The unique, database identifier for the drug to retrieve. " +
                "If null, return all doctors in the database.",example = "1024",required = true)
        private final Long id;
    }

    @ToString
    @Getter
    private static final class DrugResponsePayload{
        public DrugResponsePayload(final Drug drug) {
            id = drug.getId();
            name = drug.getName();
            description = drug.getDescription();
            formula = drug.getFormula();
        }

        private Long id;
        private String name;
        private String description;
        private String formula;
    }

    private final DrugService service;

}
