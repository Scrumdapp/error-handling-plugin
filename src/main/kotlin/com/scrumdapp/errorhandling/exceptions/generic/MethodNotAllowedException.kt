package com.scrumdapp.errorhandling.exceptions.generic

import com.scrumdapp.errorhandling.exceptions.ApplicationException
import org.slf4j.event.Level
import org.springframework.http.HttpStatus

class MethodNotAllowedException(
    cause: Throwable? = null,
    logLevel: Level = Level.DEBUG,
): ApplicationException(
    HttpStatus.METHOD_NOT_ALLOWED,
    "METHOD_NOT_ALLOWED",
    "errors.generic.methodNotAllowed.title", // Warning, translation not implemented on FE
    "errors.generic.methodNotAllowed.description",
    logLevel,
    cause,
)