
package com.scrumdapp.errorhandling.handlers

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.BadCredentialsException
import tools.jackson.databind.ObjectMapper

class ExceptionHandlerTest {

    private val exceptionHandler = ExceptionHandler(ObjectMapper())

    private fun handleAuthenticationException(exception: BadCredentialsException): MockHttpServletResponse {
        val request = MockHttpServletRequest()
        val response = MockHttpServletResponse()

        SecurityAuthenticationEntryPoint(exceptionHandler).commence(request, response, exception)

        return response
    }

    private fun handleAccessDeniedException(exception: AccessDeniedException): MockHttpServletResponse {
        val request = MockHttpServletRequest()
        val response = MockHttpServletResponse()

        SecurityAccessDeniedHandler(exceptionHandler).handle(request, response, exception)

        return response
    }

    @Test
    fun `authentication exception returns 401`() {
        val response = handleAuthenticationException(BadCredentialsException("Invalid credentials"))

        assertEquals(401, response.status)
        val json = ObjectMapper().readTree(response.contentAsString)
        assertEquals("AUTHENTICATION_REQUIRED", json["code"].stringValue())
        assertEquals("errorCode.generic.authenticationRequired.title", json["errorTitleKey"].stringValue())
        assertEquals("errorCode.generic.authenticationRequired.description", json["errorDescriptionKey"].stringValue())
    }

    @Test
    fun `access denied exception returns 403`() {
        val response = handleAccessDeniedException(AccessDeniedException("Access denied"))

        assertEquals(403, response.status)
        val json = ObjectMapper().readTree(response.contentAsString)
        assertEquals("ACCESS_DENIED", json["code"].stringValue())
        assertEquals("errorCode.generic.accessDenied.title", json["errorTitleKey"].stringValue())
        assertEquals("errorCode.generic.accessDenied.description", json["errorDescriptionKey"].stringValue())
    }
}
