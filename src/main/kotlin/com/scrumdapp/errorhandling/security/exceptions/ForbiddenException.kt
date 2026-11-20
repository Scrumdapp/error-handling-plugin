package com.scrumdapp.errorhandling.security.exceptions

import com.scrumdapp.errorhandling.mvc.exceptions.ApplicationException
import org.slf4j.event.Level
import org.springframework.http.HttpStatus

class ForbiddenException(
    cause: Throwable? = null,
    logLevel: Level = Level.DEBUG,
): ApplicationException(
    HttpStatus.FORBIDDEN,
    "ACCESS_DENIED",
    "errors.generic.accessDenied.title",
    "errors.generic.accessDenied.description",
    logLevel,
    cause,
)