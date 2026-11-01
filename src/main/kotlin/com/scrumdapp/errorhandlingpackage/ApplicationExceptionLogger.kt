package com.scrumdapp.errorhandlingpackage

import com.scrumdapp.errorhandlingpackage.exceptions.ApplicationException
import com.scrumdapp.errorhandlingpackage.exceptions.security.ForbiddenException
import com.scrumdapp.errorhandlingpackage.exceptions.security.NotAuthorizedException
import com.scrumdapp.passportplugin.jwt.PassportContent
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.event.Level
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Component

@Component
class ApplicationExceptionLogger(
    private val logger: Logger = LoggerFactory.getLogger(ApplicationExceptionLogger::class.java)
) {
    fun logException(ex: ApplicationException) {
        if (!ex.enableLogging) return

        //TODO("figure out better logging format")
        logger.atLevel(ex.logLevel).log(ex.message, if (ex.logLevel.toInt() >= Level.WARN.toInt()) ex else null)
    }

    fun logSecurityException(ex: ApplicationException, request: HttpServletRequest) {

        if (ex is NotAuthorizedException) {
            logger.info("Failed authentication attempt at {}", request.requestURI, ex)
        } else if (ex is ForbiddenException) {

            val jwt = SecurityContextHolder.getContext().authentication?.principal as Jwt

            logger.info("Failed authorization attempt by user {} at {}", jwt.subject, request.requestURI, ex)
        }
    }
}