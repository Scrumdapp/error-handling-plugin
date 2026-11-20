package com.scrumdapp.errorhandling.mvc

import com.scrumdapp.errorhandling.mvc.exceptions.ApplicationException
import com.scrumdapp.errorhandling.mvc.exceptions.ResourceNotFoundException
import com.scrumdapp.errorhandling.mvc.exceptions.InternalServerErrorException
import com.scrumdapp.errorhandling.mvc.exceptions.MediaTypeNotSupportedException
import com.scrumdapp.errorhandling.mvc.exceptions.MethodNotAllowedException
import com.scrumdapp.errorhandling.mvc.exceptions.NotAcceptableException
import com.scrumdapp.errorhandling.security.exceptions.ForbiddenException
import com.scrumdapp.errorhandling.mvc.exceptions.validation.ValidationException
import com.scrumdapp.errorhandling.mvc.exceptions.validation.ValidationFieldError
import org.springframework.beans.TypeMismatchException
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.stereotype.Component
import org.springframework.validation.FieldError
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingPathVariableException
import org.springframework.web.bind.MissingRequestHeaderException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.resource.NoResourceFoundException

@Component
class MvcExceptionConverter {

    fun convert(ex: Exception): ApplicationException {
        if (ex is ApplicationException) return ex

        return when (ex) {
            is HttpRequestMethodNotSupportedException ->
                MethodNotAllowedException(ex.cause)
            is HttpMediaTypeNotSupportedException ->
                MediaTypeNotSupportedException(ex.cause)
            is HttpMediaTypeNotAcceptableException ->
                NotAcceptableException(ex.cause)

            is MissingServletRequestParameterException ->
                ValidationException(
                    listOf(
                        ValidationFieldError(
                            "query",
                            "errors.validation.queryVariableMissing"
                        )
                    ),
                    ex.cause
                )
            is MissingPathVariableException ->
                ResourceNotFoundException(ex.cause)
            is HttpMessageNotReadableException ->
                ValidationException(
                    listOf(
                        ValidationFieldError(
                            "request",
                            "errors.validation.requestInvalid"
                        )
                    ),
                    ex.cause
                )
            is MissingRequestHeaderException ->
                ForbiddenException()
            is TypeMismatchException ->
                InternalServerErrorException(ex.cause)
            is MethodArgumentNotValidException ->
                ValidationException(
                    extractValidationErrors(ex),
                    ex.cause
                )

            is NoHandlerFoundException ->
                ResourceNotFoundException(ex.cause)
            is NoResourceFoundException ->
                ResourceNotFoundException(ex.cause)

            else ->
                InternalServerErrorException(cause = ex)
        }
    }

    private fun extractValidationErrors(ex: MethodArgumentNotValidException): List<ValidationFieldError> {
        return ex.bindingResult.allErrors.map { error ->
            ValidationFieldError(
                field = (error as FieldError).field,
                translationKey = error.defaultMessage
                    ?: "errors.validation.field.invalid" //TODO("Check if this is the default validation code")
            )
        }
    }
}