package com.example.demo.exception.CustomException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidIdException extends RuntimeException {

    private static final long serialVersionUID = 1;

    public InvalidIdException(String message) {
        super(message);
    }
}
