package com.scrumdapp.errorhandling.mvc.exceptions

import org.slf4j.event.Level
import org.springframework.http.HttpStatus

class BadRequestException(
    cause: Throwable? = null,
    logLevel: Level = Level.DEBUG
): ApplicationException(
    HttpStatus.BAD_REQUEST,
    "BAD_REQUEST",
    "errors.generic.badRequest.title",
    "errors.generic.badRequest.description",
    logLevel,
    cause,
)