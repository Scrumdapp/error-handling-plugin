package com.scrumdapp.errorhandlingpackage.exceptions.generic

import com.scrumdapp.errorhandlingpackage.exceptions.ApplicationException
import org.slf4j.event.Level
import org.springframework.http.HttpStatus

class InternalServerErrorException(
    cause: Throwable? = null,
    enableLogging: Boolean = true,
    logLevel: Level = Level.WARN
): ApplicationException(
    HttpStatus.INTERNAL_SERVER_ERROR,
    "INTERNAL_ERROR",
    "errors.generic.internal.title",
    "errors.generic.internal.description",
    logLevel,
    enableLogging,
    cause,
)
