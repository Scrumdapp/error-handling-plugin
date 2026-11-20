package com.scrumdapp.errorhandling.exceptions.validation

import com.scrumdapp.errorhandling.exceptions.ApplicationException
import org.slf4j.event.Level
import org.springframework.http.HttpStatus

class ValidationException(
    val errors: List<ValidationFieldError>,
    cause: Throwable? = null,
    logLevel: Level = Level.DEBUG,
): ApplicationException(
    HttpStatus.BAD_REQUEST,
    "VALIDATION_FAILED",
    "errors.validation.failed.title",
    "errors.validation.failed.message",
    logLevel,
    cause,
) {
     fun convertToValidationResponse(): ValidationErrorResponse {
        return ValidationErrorResponse(
            this.status.value(),
            this.message,
            this.errorTitleKey,
            this.errorDescriptionKey,
            this.errors,
        )
    }
}