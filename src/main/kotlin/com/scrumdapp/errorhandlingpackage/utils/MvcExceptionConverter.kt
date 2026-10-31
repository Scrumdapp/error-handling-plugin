package com.scrumdapp.errorhandlingpackage.utils

import com.scrumdapp.errorhandlingpackage.exceptions.ApplicationException
import com.scrumdapp.errorhandlingpackage.exceptions.generic.GenericNotFoundException
import com.scrumdapp.errorhandlingpackage.exceptions.generic.InternalServerErrorException
import com.scrumdapp.errorhandlingpackage.exceptions.generic.MediaTypeNotSupportedException
import com.scrumdapp.errorhandlingpackage.exceptions.generic.MethodNotAllowedException
import com.scrumdapp.errorhandlingpackage.exceptions.generic.NotAcceptableException
import com.scrumdapp.errorhandlingpackage.exceptions.validation.ValidationException
import com.scrumdapp.errorhandlingpackage.exceptions.validation.ValidationFieldError
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
                ValidationException(
                    listOf(
                        ValidationFieldError(
                            "path",
                            "errors.validation.pathVariableMissing"
                        )
                    ),
                    ex.cause
                )
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
                ValidationException(
                    listOf(
                        ValidationFieldError(
                            "header",
                            "errors.validation.requestHeaderMissing"
                        )
                    ),
                    ex.cause
                )
            is TypeMismatchException ->
                ValidationException(
                    listOf(
                        ValidationFieldError(
                            "path",
                            "errors.validation.pathInvalid"
                        )
                    ),
                    ex.cause
                )
            is MethodArgumentNotValidException ->
                ValidationException(
                    extractValidationErrors(ex),
                    ex.cause
                )

            is NoHandlerFoundException ->
                GenericNotFoundException(ex.cause)
            is NoResourceFoundException ->
                GenericNotFoundException(ex.cause)

            else ->
                InternalServerErrorException(cause = ex)
        }
    }

    private fun extractValidationErrors(ex: MethodArgumentNotValidException): List<ValidationFieldError> {
        return ex.bindingResult.allErrors.map { error ->
            ValidationFieldError(
                field = (error as FieldError).field,
                translationKey = error.defaultMessage ?: "errors.validation.field.invalid" //TODO("Check if this is the default validation code")
            )
        }
    }
}