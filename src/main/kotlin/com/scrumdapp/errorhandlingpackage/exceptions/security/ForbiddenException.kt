package com.scrumdapp.errorhandlingpackage.exceptions.security

import com.scrumdapp.errorhandlingpackage.exceptions.ApplicationException
import org.slf4j.event.Level
import org.springframework.http.HttpStatus

class ForbiddenException(
    cause: Throwable? = null,
    enableLogging: Boolean = false,
    logLevel: Level = Level.INFO,
): ApplicationException(
    HttpStatus.FORBIDDEN,
    "ACCESS_DENIED",
    "errors.generic.accessDenied.title",
    "errors.generic.accessDenied.description",
    logLevel,
    enableLogging,
    cause,
)