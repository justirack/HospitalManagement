package com.example.demo.exceptionHandling;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class globalHandler {

    //Handle specific exceptions

    //ResourceNotFound exception
    @ExceptionHandler(ConfigDataResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFound(ConfigDataResourceNotFoundException exception, WebRequest request){
        exceptionDetails exceptionDetails = new exceptionDetails(new Date(), exception.getMessage(), request.getDescription(false));

        return new ResponseEntity(exceptionDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleResourceNotFound(IllegalStateException exception, WebRequest request){
        exceptionDetails exceptionDetails = new exceptionDetails(new Date(), exception.getMessage(), request.getDescription(false));

        return new ResponseEntity(exceptionDetails, HttpStatus.NOT_FOUND);
    }

    //Handle global exceptions
    //ResourceNotFound exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception exception, WebRequest request){
        exceptionDetails exceptionDetails = new exceptionDetails(new Date(), exception.getMessage(), request.getDescription(false));

        return new ResponseEntity(exceptionDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
