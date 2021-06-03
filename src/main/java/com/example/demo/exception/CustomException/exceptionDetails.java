package com.example.demo.exception.CustomException;

public class exceptionDetails {

    private final String message;
    private final String details;

    public exceptionDetails(String message, String details){
        super();
        this.message = message;
        this.details = details;
    }



    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
