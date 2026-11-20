package com.scrumdapp.errorhandling.mvc.exceptions

import org.slf4j.event.Level
import org.springframework.http.HttpStatus

class DownstreamServiceUnavailableException(
    cause: Throwable? = null,
    logLevel: Level = Level.WARN,
): ApplicationException(
    HttpStatus.SERVICE_UNAVAILABLE,
    "SERVICE_UNAVAILABLE",
    "errors.generic.serviceUnavailable.title",
    "errors.generic.serviceUnavailable.description",
    logLevel,
    cause,
)