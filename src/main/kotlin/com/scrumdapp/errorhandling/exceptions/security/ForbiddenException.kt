package com.scrumdapp.errorhandling.exceptions.security

import com.scrumdapp.errorhandling.exceptions.ApplicationException
import org.slf4j.event.Level
import org.springframework.http.HttpStatus

class ForbiddenException(
    cause: Throwable? = null,
    enableLogging: Boolean = true,
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