package com.scrumdapp.errorhandlingpackage.exceptions

import org.slf4j.event.Level
import org.springframework.http.HttpStatus

abstract class ApplicationException(
    val status: HttpStatus,
    message: String,
    val errorTitleKey: String,
    val errorDescriptionKey: String,
    val logLevel: Level,
    val enableLogging: Boolean,
    cause: Throwable?
): RuntimeException(message, cause) {
    fun convertToResponse(): ErrorResponse {
        return ErrorResponse(
            this.status.value(),
            this.message,
            this.errorTitleKey,
            this.errorDescriptionKey,
        )
    }
}

