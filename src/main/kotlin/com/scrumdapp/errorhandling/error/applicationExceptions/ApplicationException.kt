package com.scrumdapp.errorhandling.error.applicationExceptions

import com.scrumdapp.errorhandling.error.ErrorCode
import org.springframework.http.HttpStatus

open class ApplicationException(
    open val status: HttpStatus,
    open val body: ErrorCode,
    val enableLogging: Boolean = false,
    val enableStacktrace: Boolean = false,
) : RuntimeException()