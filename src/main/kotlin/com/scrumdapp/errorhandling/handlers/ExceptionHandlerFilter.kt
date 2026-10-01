package com.scrumdapp.errorhandling.handlers

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


// This filter only triggers to catch security exceptions and exception missed by the ServletHandlerExceptionResolver
@Component
class ExceptionHandlerFilter(
    private val handler: ExceptionHandler,
): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        }

        catch (ex: Exception) {

            val rootException = findRootThrowable(ex)
            handler.handleException(request, response, rootException)
        }
    }

    private fun findRootThrowable(throwable: Exception): Exception {
        var result = throwable

        while (result.cause != null && result.cause !== result) {
            result = result.cause as Exception
        }

        return result
    }
}

