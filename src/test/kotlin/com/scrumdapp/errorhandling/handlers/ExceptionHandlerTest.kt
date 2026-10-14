
package com.scrumdapp.errorhandling.handlers

import com.scrumdapp.errorhandling.error.ErrorCode
import jakarta.servlet.http.HttpServletRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.BadCredentialsException
import tools.jackson.databind.ObjectMapper

class ExceptionHandlerTest {

    private val handler = ExceptionHandler(ObjectMapper())

    private fun handle(exception: Exception): MockHttpServletResponse {
        val request = MockHttpServletRequest()
        val response = MockHttpServletResponse()

        handler.handleException(request, response, exception)

        return response
    }

    @Test
    fun `authentication exception returns 401`() {
        val response = handle(BadCredentialsException("Invalid credentials"))

        assertEquals(401, response.status)
        assertEquals(true, response.contentAsString.contains("AUTHENTICATION_REQUIRED"))
    }

    @Test
    fun `access denied exception returns 403`() {
        val response = handle(AccessDeniedException("Access denied"))

        assertEquals(403, response.status)
        assertEquals(true, response.contentAsString.contains("ACCESS_DENIED"))
    }
}
