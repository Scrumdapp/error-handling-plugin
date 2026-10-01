package com.scrumdapp.errorhandling.error.applicationExceptions

import com.scrumdapp.errorhandling.error.ErrorCode
import org.springframework.http.HttpStatus

class BadRequestException(
    override val status: HttpStatus = HttpStatus.BAD_REQUEST,
    override val body: ErrorCode = ErrorCode.BAD_REQUEST
): ApplicationException(status, body)

class NotAuthorizedException(
    override val status: HttpStatus = HttpStatus.UNAUTHORIZED,
    override val body: ErrorCode = ErrorCode.AUTHENTICATION_REQUIRED
): ApplicationException(status, body)

class ForbiddenException(
    override val status: HttpStatus = HttpStatus.FORBIDDEN,
    override val body: ErrorCode = ErrorCode.ACCESS_DENIED
): ApplicationException(status, body)

class NotFoundException(
    override val status: HttpStatus = HttpStatus.NOT_FOUND,
    override val body: ErrorCode = ErrorCode.NOT_FOUND
): ApplicationException(status, body)

class ServerFaultException(
    override val status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    override val body: ErrorCode = ErrorCode.INTERNAL_ERROR
): ApplicationException(status, body, enableLogging = true, enableStacktrace = true)

class ServiceUnavailableException(
    override val status: HttpStatus = HttpStatus.SERVICE_UNAVAILABLE,
    override val body: ErrorCode = ErrorCode.SERVICE_UNAVAILABLE
): ApplicationException(status, body, enableLogging = true)
