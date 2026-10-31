package com.scrumdapp.errorhandlingpackage.exceptions.generic

import com.scrumdapp.errorhandlingpackage.exceptions.ApplicationException
import org.slf4j.event.Level
import org.springframework.http.HttpStatus

class NotAcceptableException(
    cause: Throwable? = null,
    enableLogging: Boolean = false,
    logLevel: Level = Level.WARN,
): ApplicationException(
    HttpStatus.NOT_ACCEPTABLE,
    "NOT_ACCEPTABLE",
    "errors.generic.notAcceptable.title", // Warning, translation not implemented on FE
    "errors.generic.notAcceptable.description",
    logLevel,
    enableLogging,
    cause,
)