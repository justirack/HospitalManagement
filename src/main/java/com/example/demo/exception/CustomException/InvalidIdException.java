package com.example.demo.exception.CustomException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class InvalidIdException extends RuntimeException {

    private static final long serialVersionUID = 1;

    public InvalidIdException() {
        super();
    }

    public InvalidIdException(String message) {
        super(message);
    }
}
