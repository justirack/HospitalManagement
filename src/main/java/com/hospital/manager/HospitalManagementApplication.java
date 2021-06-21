/*
  Copyright 2021, Justin Rackley, All rights reserved.
*/
package com.hospital.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is the main entry point for this application.
 */
@SpringBootApplication
public class HospitalManagementApplication {

    public static void main(final String[] arguments) {
        SpringApplication.run(HospitalManagementApplication.class, arguments);
    }
}