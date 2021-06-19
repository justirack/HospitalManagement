/*
  Copyright 2021, Justin Rackley, All rights reserved.
*/
package com.hospital.manager.exception.CustomException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * This class represents the details that will be sent to the user when an exception handled.
 */
@Getter
@RequiredArgsConstructor
public final class ExceptionDetails {
    private final String message;
    private final String details;
    private final int statusCode;
}