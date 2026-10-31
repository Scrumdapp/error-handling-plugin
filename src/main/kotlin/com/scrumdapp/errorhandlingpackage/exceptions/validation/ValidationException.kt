package com.scrumdapp.errorhandlingpackage.exceptions.validation

import com.scrumdapp.errorhandlingpackage.exceptions.ApplicationException
import com.scrumdapp.errorhandlingpackage.exceptions.ErrorResponse
import com.scrumdapp.errorhandlingpackage.exceptions.ValidationErrorResponse
import org.slf4j.event.Level
import org.springframework.http.HttpStatus

class ValidationException(
    val errors: List<ValidationFieldError>,
    cause: Throwable? = null,
    enableLogging: Boolean = false,
    logLevel: Level = Level.DEBUG,
): ApplicationException(
    HttpStatus.BAD_REQUEST,
    "VALIDATION_FAILED",
    "errors.validation.failed.title",
    "errors.validation.failed.message",
    logLevel,
    enableLogging,
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