package com.scrumdapp.errorhandling.handlers

import com.scrumdapp.errorhandling.error.applicationExceptions.ApplicationException
import com.scrumdapp.errorhandling.errorResponses.ErrorResponse
import com.scrumdapp.errorhandling.errorResponses.ValidationErrorResponse
import com.scrumdapp.errorhandling.error.ErrorCode
import com.scrumdapp.errorhandling.error.ValidationErrorType
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.http.converter.HttpMessageNotWritableException
import org.springframework.stereotype.Component
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingPathVariableException
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.client.ResourceAccessException
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.resource.NoResourceFoundException
import tools.jackson.databind.ObjectMapper
import java.lang.Exception

@Component
class ExceptionHandler(
    private val objectMapper: ObjectMapper,
    private val logger: Logger = LoggerFactory.getLogger(ExceptionHandler::class.java)
) {
    fun handleException(req: HttpServletRequest, res: HttpServletResponse, ex: Exception) {

        val error = mapException(ex)
        logException(req, error, ex)

        res.status = error.status
        res.contentType = MediaType.APPLICATION_JSON_VALUE
        res.characterEncoding = Charsets.UTF_8.name()

        objectMapper.writeValue(res.outputStream, error)
    }

    private fun mapException(ex: Exception): ErrorResponse {

        return when (ex) {
            is ApplicationException ->
                handleApplicationException(ex)

            // Jakarta validation errors
            is MethodArgumentNotValidException ->
                handleValidationException(ex)

            // Downstream errors
            is ResourceAccessException ->
                handleDownstreamExceptions(ex)
            is HttpServerErrorException ->
                ErrorCode.SERVICE_UNAVAILABLE.toErrorResponse(HttpStatus.SERVICE_UNAVAILABLE)

            // MVC errors
            is HttpRequestMethodNotSupportedException ->
                // Should replace body with 405 error
                ErrorCode.BAD_REQUEST.toErrorResponse(HttpStatus.METHOD_NOT_ALLOWED)

            is HttpMediaTypeNotAcceptableException ->
                // Should replace body with actual 415 error (:
                ErrorCode.BAD_REQUEST.toErrorResponse(HttpStatus.NOT_ACCEPTABLE)

            is HttpMediaTypeNotSupportedException ->
                ErrorCode.BAD_REQUEST.toErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE)

            is MissingPathVariableException ->
                // This gets thrown whenever a path variable is not implemented correctly
                ErrorCode.INTERNAL_ERROR.toErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR)

            is NoHandlerFoundException ->
                ErrorCode.NOT_FOUND.toErrorResponse(HttpStatus.NOT_FOUND)

            is NoResourceFoundException ->
                ErrorCode.NOT_FOUND.toErrorResponse(HttpStatus.NOT_FOUND)

            is HttpMessageNotReadableException ->
                ErrorCode.BAD_REQUEST.toErrorResponse(HttpStatus.BAD_REQUEST)

            is HttpMessageNotWritableException ->
                ErrorCode.INTERNAL_ERROR.toErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR)
            else ->
                ErrorCode.INTERNAL_ERROR.toErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    private fun handleApplicationException(ex: ApplicationException): ErrorResponse {
        return ex.body.toErrorResponse(ex.status)
    }

    private fun handleValidationException(ex: MethodArgumentNotValidException): ErrorResponse {

        val validationErrors = ex.bindingResult.fieldErrors.map { fieldError ->

            val validationType = ValidationErrorType.fromFieldError(fieldError)

            ValidationErrorResponse(
                field = fieldError.field,
                translationKey = "errorCode.validation.${fieldError.field}.${validationType.key}"
            )
        }

        val errorCode = ErrorCode.VALIDATION_FAILED
        return errorCode.toErrorResponse(HttpStatus.BAD_REQUEST, validationErrors)
    }

    // Not sure how to handle this yet
    private fun handleDownstreamExceptions(ex: ResourceAccessException): ErrorResponse {
        val error = ex.rootCause
        return ErrorCode.SERVICE_UNAVAILABLE.toErrorResponse(HttpStatus.SERVICE_UNAVAILABLE)
    }


    private fun logException(req: HttpServletRequest, error: ErrorResponse, ex: Exception) {

        when {
            error.status == HttpStatus.INTERNAL_SERVER_ERROR.value() -> {
                logger.error("Unhandled error: [{} {}]", req.method, req.requestURI, ex)
            }

            error.status == HttpStatus.SERVICE_UNAVAILABLE.value() -> {
                logger.warn("Downstream error: [{} {}]", req.method, req.requestURI, ex)
            }

            ex is ApplicationException -> {
                if (ex.enableLogging && ex.enableStacktrace) {
                    logger.warn("Application error: [{} {}]", req.method, req.requestURI, ex)
                } else if (ex.enableLogging) {
                    logger.debug("Application error: [{} {}] {}", req.method, req.requestURI, ex.message)
                }
            }
        }
    }
}