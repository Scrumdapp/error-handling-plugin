package com.scrumdapp.errorhandling.handlers

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.ModelAndView
import java.io.IOException
import java.lang.Exception


@Component
class CustomHandlerExceptionResolver(
    private val exceptionHandler: ExceptionHandler
): HandlerExceptionResolver {

    private val logger = LoggerFactory.getLogger(CustomHandlerExceptionResolver::class.java)

    override fun resolveException(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any?,
        ex: Exception
    ): ModelAndView? {
        try {
            exceptionHandler.handleException(request, response, ex)
            return ModelAndView()
        } catch (e: IOException) {
            logger.warn("Unable to handle exception with the exception resolver. Cause {}", e.cause, e)
            return null
        }
    }
}