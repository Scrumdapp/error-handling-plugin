package com.scrumdapp.errorhandling.security.exceptions

import com.scrumdapp.errorhandling.mvc.exceptions.ApplicationException
import org.slf4j.event.Level
import org.springframework.http.HttpStatus

class NotAuthorizedException(
    cause: Throwable? = null,
    logLevel: Level = Level.INFO,
): ApplicationException(
    HttpStatus.UNAUTHORIZED,
    "AUTHENTICATION_REQUIRED",
    "errors.generic.authenticationRequired.title",
    "errors.generic.authenticationRequired.description",
    logLevel,
    cause,
)