package com.scrumdapp.errorhandling.mvc.exceptions

import org.slf4j.event.Level
import org.springframework.http.HttpStatus

class MediaTypeNotSupportedException(
    cause: Throwable? = null,
    logLevel: Level = Level.DEBUG,
): ApplicationException(
    HttpStatus.UNSUPPORTED_MEDIA_TYPE,
    "MEDIA_TYPE_NOT_SUPPORTED",
    "errors.generic.mediaTypeNotSupported.title", // Warning, translation not implemented on FE
    "errors.generic.mediaTypeNotSupported.description",
    logLevel,
    cause,
)