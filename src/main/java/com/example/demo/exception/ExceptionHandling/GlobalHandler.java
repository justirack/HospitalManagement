package com.example.demo.exception.ExceptionHandling;

import com.example.demo.exception.CustomException.InvalidIdException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

import com.example.demo.exception.CustomException.exceptionDetails;

public class GlobalHandler {

    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<?> handleInvalidId(InvalidIdException exception, WebRequest request){
        exceptionDetails details = new exceptionDetails(new Date(), exception.getMessage(), request.getDescription(false));

        return new ResponseEntity(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalState(IllegalStateException exception, WebRequest request){
        exceptionDetails exceptionDetails = new exceptionDetails(new Date(), exception.getMessage(), request.getDescription(false));

        return new ResponseEntity(exceptionDetails, HttpStatus.BAD_REQUEST);
    }
}
