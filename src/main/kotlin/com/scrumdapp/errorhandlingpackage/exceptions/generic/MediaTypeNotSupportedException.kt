package com.scrumdapp.errorhandlingpackage.exceptions.generic

import com.scrumdapp.errorhandlingpackage.exceptions.ApplicationException
import org.slf4j.event.Level
import org.springframework.http.HttpStatus

class MediaTypeNotSupportedException(
    cause: Throwable? = null,
    enableLogging: Boolean = false,
    logLevel: Level = Level.DEBUG,
): ApplicationException(
    HttpStatus.UNSUPPORTED_MEDIA_TYPE,
    "MEDIA_TYPE_NOT_SUPPORTED",
    "errors.generic.mediaTypeNotSupported.title", // Warning, translation not implemented on FE
    "errors.generic.mediaTypeNotSupported.description",
    logLevel,
    enableLogging,
    cause,
)