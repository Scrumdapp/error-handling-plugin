package com.scrumdapp.errorhandling

import com.scrumdapp.errorhandling.exceptions.ApplicationException
import com.scrumdapp.errorhandling.exceptions.security.ForbiddenException
import com.scrumdapp.errorhandling.exceptions.security.NotAuthorizedException
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Component

@Component
class ApplicationExceptionLogger(
    private val logger: Logger = LoggerFactory.getLogger(ApplicationExceptionLogger::class.java)
) {
    fun logException(ex: ApplicationException, request: HttpServletRequest) {

        if (ex is ForbiddenException || ex is NotAuthorizedException) {
            logSecurityException(ex, request)
        }

        logger.atLevel(ex.logLevel).log(ex.message, ex)
    }

    fun logSecurityException(ex: ApplicationException, request: HttpServletRequest) {

        if (ex is NotAuthorizedException) {
            logger.atLevel(ex.logLevel).log("Failed authentication attempt at {}", request.requestURI, ex)
        } else if (ex is ForbiddenException) {
            val jwt = SecurityContextHolder.getContext().authentication?.principal as Jwt

            logger.atLevel(ex.logLevel).log("Failed authorization attempt by user {} at {}", jwt.subject, request.requestURI, ex)
        }
    }
}