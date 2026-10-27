package com.scrumdapp.errorhandling.handlers

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler

class SecurityAccessDeniedHandler(
    private val handler: ExceptionHandler
) : AccessDeniedHandler {

    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        handler.handleException(request, response, accessDeniedException)
    }
}
