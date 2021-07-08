/*
  Copyright 2021, Justin Rackley, All rights reserved.
*/
package com.hospital.manager.exception.ExceptionHandling;

import com.hospital.manager.exception.CustomException.AppointmentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hospital.manager.exception.CustomException.ExceptionDetails;
import com.hospital.manager.exception.CustomException.FailedRequestException;
import com.hospital.manager.exception.CustomException.InvalidIdException;

@ControllerAdvice
public class GlobalHandler {

    /**
     * <p>
     *     Handler for InvalidId exceptions.
     * </p>>
     * @param exception The exception to be handled.
     * @return The handled exception.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidIdException.class)
    public final ResponseEntity<Object> handleInvalidId(InvalidIdException exception){
        //Get the exception details to send to the user from the exception call
        final String details = exception.getLocalizedMessage();
        //set the error message that we will send to the user
        final String message = "The ID you entered was invalid. Please enter a valid id and try again";
        //set the status code of the error
        final int statusCode = HttpStatus.BAD_REQUEST.value();
        //create an ExceptionDetails object to return
        final ExceptionDetails exceptionDetails = new ExceptionDetails(message,details, statusCode);
        //return the ExceptionDetails object and the status code
        return new ResponseEntity<>(exceptionDetails,HttpStatus.BAD_REQUEST);
    }

    /**
     * <p>
     *     Handler for FailedRequest exceptions.
     * </p>>
     * @param exception The exception to be handled.
     * @return The handled Exception.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FailedRequestException.class)
    public final ResponseEntity<Object> handleFailedRequest(FailedRequestException exception){
        final String details = exception.getLocalizedMessage();
        final String message = "Your request failed. Please make sure all information is correct and try again";
        final int statusCode = HttpStatus.BAD_REQUEST.value();
        final ExceptionDetails exceptionDetails = new ExceptionDetails(message,details,statusCode);
        return new ResponseEntity<>(exceptionDetails,HttpStatus.BAD_REQUEST);
    }

    /**
     * <p>
     *     Handler for AppointmentNotFound exceptions.
     * </p>
     * @param exception The exception to be handled
     * @return The handled exception.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AppointmentNotFoundException.class)
    public final ResponseEntity<Object> handleAppointmentNotFound(AppointmentNotFoundException exception){
        final String details = exception.getLocalizedMessage();
        final String message = "The request to get your appointment details or a list of all appointments failed. " +
                "Please make sure all information is correct and try again";
        final int statusCode = HttpStatus.NOT_FOUND.value();
        final ExceptionDetails exceptionDetails = new ExceptionDetails(message,details,statusCode);
        return new ResponseEntity<>(exceptionDetails,HttpStatus.NOT_FOUND);
    }

    /**
     * <p>
     *     Global handler for any other unhandled exception.
     * </p>>
     * @param exception The exception to be handled.
     * @return The handled exception.
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleException(Exception exception){
        //Get the exception details to send to the user from the exception call
        final String details = exception.getLocalizedMessage();
        //set the error message that we will send to the user
        final String message = "The error could not be handled.";
        //set the status code of the error
        final int statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        //create an ExceptionDetails object to return
        final ExceptionDetails exceptionDetails = new ExceptionDetails(message,details, statusCode);
        //return the ExceptionDetails object and the status code
        return new ResponseEntity<>(exceptionDetails,HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
