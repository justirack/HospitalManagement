package com.example.demo.exception.ExceptionHandling;

import com.example.demo.exception.CustomException.InvalidIdException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.exception.CustomException.exceptionDetails;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidIdException.class)
    public final ResponseEntity<Object> handleInvalidId(InvalidIdException exception){
        String details = exception.getLocalizedMessage();
        String message = "The ID you entered was invalid. Please enter a valid id and try again";
        final int statusCode = HttpStatus.NOT_FOUND.value();
        exceptionDetails exceptionDetails = new exceptionDetails(message,details, statusCode);
        return new ResponseEntity<>(exceptionDetails,HttpStatus.BAD_REQUEST);
    }

}
