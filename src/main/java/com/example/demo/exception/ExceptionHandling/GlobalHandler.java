package com.example.demo.exception.ExceptionHandling;

import com.example.demo.exception.CustomException.ExceptionDetails;
import com.example.demo.exception.CustomException.InvalidIdException;
import com.example.demo.exception.CustomException.FailedRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalHandler {

    /**
     * Handler for InvalidId exceptions.
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
     * Handler for FailedRequest exceptions.
     * Same idea as handleInvalidId so read there for in-line comments.
     * @param exception The exception to be handled.
     * @return The handled Exception.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FailedRequestException.class)
    public final ResponseEntity<Object> handleFailedRequest(FailedRequestException exception){
        final String details = exception.getLocalizedMessage();
        final String message = "Your request failed. Please make sure the information entered is correct";
        final int statusCode = HttpStatus.BAD_REQUEST.value();
        final ExceptionDetails exceptionDetails = new ExceptionDetails(message,details,statusCode);
        return new ResponseEntity<>(exceptionDetails,HttpStatus.BAD_REQUEST);
    }

    /**
     * Global handler for any other unhandled exception.
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
