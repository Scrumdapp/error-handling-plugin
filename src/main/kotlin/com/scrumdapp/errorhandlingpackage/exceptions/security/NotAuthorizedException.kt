package com.scrumdapp.errorhandlingpackage.exceptions.security

import com.scrumdapp.errorhandlingpackage.exceptions.ApplicationException
import org.slf4j.event.Level
import org.springframework.http.HttpStatus

class NotAuthorizedException(
    cause: Throwable? = null,
    enableLogging: Boolean = true,
    logLevel: Level = Level.INFO,
): ApplicationException(
    HttpStatus.UNAUTHORIZED,
    "AUTHENTICATION_REQUIRED",
    "errors.generic.authenticationRequired.title",
    "errors.generic.authenticationRequired.description",
    logLevel,
    enableLogging,
    cause,
)