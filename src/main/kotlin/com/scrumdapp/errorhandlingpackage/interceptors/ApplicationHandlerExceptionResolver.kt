package com.scrumdapp.errorhandlingpackage.interceptors

import com.scrumdapp.errorhandlingpackage.ApplicationExceptionLogger
import com.scrumdapp.errorhandlingpackage.exceptions.validation.ValidationException
import com.scrumdapp.errorhandlingpackage.utils.MvcExceptionConverter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.ModelAndView
import tools.jackson.databind.ObjectMapper
import java.lang.Exception

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class ApplicationHandlerExceptionResolver(
    private val exceptionConverter: MvcExceptionConverter,
    private val logger: ApplicationExceptionLogger,
    private val objectMapper: ObjectMapper
): HandlerExceptionResolver {
    override fun resolveException(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any?,
        ex: Exception
    ): ModelAndView? {
        val applicationException = exceptionConverter.convert(ex)

        logger.logException(applicationException)

        if (response.isCommitted) {
            return ModelAndView()
        }

        response.status = applicationException.status.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE

        val body = if (applicationException is ValidationException) {
            applicationException.convertToValidationResponse()
        } else {
            applicationException.convertToResponse()
        }
        objectMapper.writeValue(response.outputStream, body)
        return ModelAndView()
    }
}