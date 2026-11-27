package com.scrumdapp.errorhandling.security

import com.scrumdapp.errorhandling.logging.ApplicationExceptionLogger
import com.scrumdapp.errorhandling.mvc.exceptions.ApplicationException
import com.scrumdapp.errorhandling.security.exceptions.ForbiddenException
import com.scrumdapp.errorhandling.security.exceptions.NotAuthorizedException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Component
import tools.jackson.databind.ObjectMapper

@Component
class SecurityExceptionResolver(
    private val logger: ApplicationExceptionLogger,
    private val objectMapper: ObjectMapper = ObjectMapper(),
) {

    fun resolveException(ex: RuntimeException, req: HttpServletRequest, res: HttpServletResponse) {
        val applicationException = when (ex) {
            is AccessDeniedException ->
                ForbiddenException(ex.cause)
            is AuthenticationException ->
                NotAuthorizedException(ex.cause)
            else ->
                NotAuthorizedException(ex)
        }

        logger.logSecurityException(applicationException, req)

        handleResponse(applicationException, res)
    }

    private fun handleResponse(applicationException: ApplicationException, response: HttpServletResponse) {
        response.status = applicationException.status.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE

        objectMapper.writeValue(response.outputStream, applicationException.convertToResponse())
    }
}