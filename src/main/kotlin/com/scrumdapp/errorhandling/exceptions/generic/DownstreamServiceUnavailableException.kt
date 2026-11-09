package com.scrumdapp.errorhandling.exceptions.generic

import com.scrumdapp.errorhandling.exceptions.ApplicationException
import org.slf4j.event.Level
import org.springframework.http.HttpStatus

class DownstreamServiceUnavailableException(
    cause: Throwable? = null,
    enableLogging: Boolean = true,
    logLevel: Level = Level.WARN,
): ApplicationException(
    HttpStatus.SERVICE_UNAVAILABLE,
    "SERVICE_UNAVAILABLE",
    "errors.generic.serviceUnavailable.title",
    "errors.generic.serviceUnavailable.description",
    logLevel,
    enableLogging,
    cause,
)