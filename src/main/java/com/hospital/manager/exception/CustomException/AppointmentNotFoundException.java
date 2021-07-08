/*
  Copyright 2021, Justin Rackley, All rights reserved.
*/
package com.hospital.manager.exception.CustomException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AppointmentNotFoundException extends RuntimeException {

    final static long serialVersionUID = 1;

    public AppointmentNotFoundException (String message){
        super(message);
    }
}
