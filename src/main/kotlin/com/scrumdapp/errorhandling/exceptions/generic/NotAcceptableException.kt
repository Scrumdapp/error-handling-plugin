package com.scrumdapp.errorhandling.exceptions.generic

import com.scrumdapp.errorhandling.exceptions.ApplicationException
import org.slf4j.event.Level
import org.springframework.http.HttpStatus

class NotAcceptableException(
    cause: Throwable? = null,
    logLevel: Level = Level.DEBUG,
): ApplicationException(
    HttpStatus.NOT_ACCEPTABLE,
    "NOT_ACCEPTABLE",
    "errors.generic.notAcceptable.title", // Warning, translation not implemented on FE
    "errors.generic.notAcceptable.description",
    logLevel,
    cause,
)