package com.scrumdapp.errorhandling.handler

import com.scrumdapp.errorhandling.dto.ErrorResponse
import com.scrumdapp.errorhandling.dto.ValidationError
import com.scrumdapp.errorhandling.error.ErrorCode
import com.scrumdapp.errorhandling.error.ValidationErrorType
import com.scrumdapp.errorhandling.exception.ApplicationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException


@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException::class)
    fun handleApplicationException(
        exception: ApplicationException
    ): ResponseEntity<ErrorResponse> {

        val response = ErrorResponse(
            status = exception.status.value(),
            code = exception.errorCode.code,
            errorTitleKey = exception.errorCode.errorTitleKey,
            errorDescriptionKey = exception.errorCode.errorDescriptionKey
        )

        return ResponseEntity
            .status(exception.status)
            .body(response)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatch(
        exception: MethodArgumentTypeMismatchException
    ): ResponseEntity<ErrorResponse> {

        val errorCode = ErrorCode.BAD_REQUEST

        val response = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            code = errorCode.code,
            errorTitleKey = errorCode.errorTitleKey,
            errorDescriptionKey = errorCode.errorDescriptionKey

        )

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(response)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(
        exception: MethodArgumentNotValidException
    ): ResponseEntity<ErrorResponse> {

        val errorCode = ErrorCode.VALIDATION_FAILED

        val validationErrors = exception.bindingResult.fieldErrors.map { fieldError ->

            val validationType =
                ValidationErrorType.fromFieldError(fieldError)

            ValidationError(
                field = fieldError.field,
                translationKey = "errorCode.validation.${fieldError.field}.${validationType.key}"
            )
        }

        val response = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            code = errorCode.code,
            errorTitleKey = errorCode.errorTitleKey,
            errorDescriptionKey = errorCode.errorDescriptionKey,
            errors = validationErrors
        )

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(response)
    }
}