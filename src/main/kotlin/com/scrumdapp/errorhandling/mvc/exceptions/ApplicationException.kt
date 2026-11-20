package com.scrumdapp.errorhandling.mvc.exceptions

import org.slf4j.event.Level
import org.springframework.http.HttpStatus

abstract class ApplicationException(
    val status: HttpStatus,
    message: String,
    val errorTitleKey: String,
    val errorDescriptionKey: String,
    val logLevel: Level,
    cause: Throwable?
): RuntimeException(message, cause) {
    fun convertToResponse(): ApplicationErrorResponse {
        return ApplicationErrorResponse(
            this.status.value(),
            this.message,
            this.errorTitleKey,
            this.errorDescriptionKey,
        )
    }
}

