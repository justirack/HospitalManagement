package com.example.demo.exception.CustomException;

/**
 * This class represents the details that will be sent to the user when an exception handled.
 */
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
