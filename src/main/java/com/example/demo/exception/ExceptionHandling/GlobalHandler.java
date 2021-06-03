package com.example.demo.exception.ExceptionHandling;

import com.example.demo.exception.CustomException.InvalidIdException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.exception.CustomException.exceptionDetails;

@ControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(InvalidIdException.class)
    public final ResponseEntity<Object> handleInvalidId(InvalidIdException exception, WebRequest request){
        String details = exception.getLocalizedMessage();
        String message = "The ID you entered was invalid. Please enter a valid id and try again";
        exceptionDetails exceptionDetails = new exceptionDetails(message,details);
        return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
    }

}
