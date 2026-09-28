package com.scrumdapp.errorhandling.exception

import com.scrumdapp.errorhandling.error.ErrorCode
import org.springframework.http.HttpStatus

open class ApplicationException(
    val status: HttpStatus,
    val errorCode: ErrorCode,
) : RuntimeException()