package com.scrumdapp.errorhandlingpackage.exceptions.generic

import com.scrumdapp.errorhandlingpackage.exceptions.ApplicationException
import org.slf4j.event.Level
import org.springframework.http.HttpStatus

class MethodNotAllowedException(
    cause: Throwable? = null,
    enableLogging: Boolean = false,
    logLevel: Level = Level.WARN,
): ApplicationException(
    HttpStatus.METHOD_NOT_ALLOWED,
    "METHOD_NOT_ALLOWED",
    "errors.generic.methodNotAllowed.title", // Warning, translation not implemented on FE
    "errors.generic.methodNotAllowed.description",
    logLevel,
    enableLogging,
    cause,
)