package com.scrumdapp.errorhandling.mvc.exceptions

import org.slf4j.event.Level
import org.springframework.http.HttpStatus

class ResourceNotFoundException(
    cause: Throwable? = null,
    logLevel: Level = Level.DEBUG
): ApplicationException(
    HttpStatus.NOT_FOUND,
    "NOT_FOUND",
    "errors.generic.notFound.title",
    "errors.generic.notFound.description",
    logLevel,
    cause,
)