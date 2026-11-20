package com.scrumdapp.errorhandling.mvc.exceptions

import org.slf4j.event.Level
import org.springframework.http.HttpStatus

class InternalServerErrorException(
    cause: Throwable? = null,
    logLevel: Level = Level.WARN
): ApplicationException(
    HttpStatus.INTERNAL_SERVER_ERROR,
    "INTERNAL_ERROR",
    "errors.generic.internal.title",
    "errors.generic.internal.description",
    logLevel,
    cause,
)
