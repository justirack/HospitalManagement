package com.example.demo.exception.CustomException;

import org.springframework.http.HttpStatus;

public class exceptionDetails {

    private final String message;
    private final String details;
    private final int statusCode;

    public exceptionDetails(String message, String details, int statusCode){
        super();
        this.message = message;
        this.details = details;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public int getStatus() {
        return statusCode;
    }
}
