package com.scrumdapp.errorhandlingpackage.exceptions.generic

import com.scrumdapp.errorhandlingpackage.exceptions.ApplicationException
import org.slf4j.event.Level
import org.springframework.http.HttpStatus

class GenericNotFoundException(
    cause: Throwable? = null,
    enableLogging: Boolean = true,
    logLevel: Level = Level.DEBUG
): ApplicationException(
    HttpStatus.NOT_FOUND,
    "NOT_FOUND",
    "errors.generic.notFound.title",
    "errors.generic.notFound.description",
    logLevel,
    enableLogging,
    cause,
)