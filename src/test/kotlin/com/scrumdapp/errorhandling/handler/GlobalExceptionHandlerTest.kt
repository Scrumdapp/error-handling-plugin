package com.scrumdapp.errorhandling.handler

import com.scrumdapp.errorhandling.error.ErrorCode
import com.scrumdapp.errorhandling.exception.ApplicationException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.validation.beanvalidation.SpringValidatorAdapter
import jakarta.validation.Validation


data class TestRequest(
    @field:NotBlank
    @field:Size(min = 3, max = 5)
    val name: String
)
class GlobalExceptionHandlerTest {

    @Test
    fun `application exception is converted to correct error response`() {
        val handler = GlobalExceptionHandler()

        val exception = ApplicationException(
            status = HttpStatus.NOT_FOUND,
            errorCode = ErrorCode.GROUP_NOT_FOUND,
        )

        val result = handler.handleApplicationException(exception)

        assertEquals(HttpStatus.NOT_FOUND, result.statusCode)
        assertEquals(404, result.body?.status)
        assertEquals("GROUP_NOT_FOUND", result.body?.code)
        assertEquals("errorCode.group.notFound.title", result.body?.errorTitleKey)
        assertEquals(
            "errorCode.group.notFound.description",
            result.body?.errorDescriptionKey
        )
    }


    @Test
    fun `unknown validation type returns invalid translation key`() {
        val handler = GlobalExceptionHandler()

        val bindingResult = mock(BindingResult::class.java)
        val exception = mock(MethodArgumentNotValidException::class.java)

        val fieldError = FieldError(
            "request",
            "name",
            "",
            false,
            arrayOf("UnknownValidation"),
            null,
            "Unknown validation error"
        )

        `when`(exception.bindingResult).thenReturn(bindingResult)
        `when`(bindingResult.fieldErrors).thenReturn(listOf(fieldError))

        val result = handler.handleValidationException(exception)

        assertEquals(HttpStatus.BAD_REQUEST, result.statusCode)
        assertEquals("VALIDATION_FAILED", result.body?.code)
        assertEquals(
            "errorCode.validation.name.invalid",
            result.body?.errors?.first()?.translationKey
        )
    }
    @Test
    fun `method argument type mismatch returns bad request`() {
        val handler = GlobalExceptionHandler()

        val exception = mock(MethodArgumentTypeMismatchException::class.java)

        val result = handler.handleMethodArgumentTypeMismatch(exception)

        assertEquals(HttpStatus.BAD_REQUEST, result.statusCode)
        assertEquals(400, result.body?.status)
        assertEquals("BAD_REQUEST", result.body?.code)
        assertEquals(
            "errorCode.generic.badRequest.title",
            result.body?.errorTitleKey
        )
        assertEquals(
            "errorCode.generic.badRequest.description",
            result.body?.errorDescriptionKey
        )
    }
    @Test
    fun `not blank validation returns required translation key`() {
        val handler = GlobalExceptionHandler()

        val request = TestRequest(name = "")
        val bindingResult = BeanPropertyBindingResult(request, "request")

        val validator = Validation.buildDefaultValidatorFactory().validator
        SpringValidatorAdapter(validator).validate(request, bindingResult)

        val exception = mock(MethodArgumentNotValidException::class.java)
        `when`(exception.bindingResult).thenReturn(bindingResult)

        val result = handler.handleValidationException(exception)

        assertEquals(HttpStatus.BAD_REQUEST, result.statusCode)
        assertEquals("VALIDATION_FAILED", result.body?.code)

        val requiredError = result.body?.errors?.find {
            it.translationKey == "errorCode.validation.name.required"
        }

        assertEquals("name", requiredError?.field)
        assertEquals(
            "errorCode.validation.name.required",
            requiredError?.translationKey
        )
    }
    @Test
    fun `size validation below minimum returns min length translation key`() {
        val handler = GlobalExceptionHandler()

        val request = TestRequest(name = "ab")
        val bindingResult = BeanPropertyBindingResult(request, "request")

        val validator = Validation.buildDefaultValidatorFactory().validator
        SpringValidatorAdapter(validator).validate(request, bindingResult)

        val exception = mock(MethodArgumentNotValidException::class.java)
        `when`(exception.bindingResult).thenReturn(bindingResult)

        val result = handler.handleValidationException(exception)

        assertEquals(HttpStatus.BAD_REQUEST, result.statusCode)
        assertEquals("VALIDATION_FAILED", result.body?.code)
        assertEquals("name", result.body?.errors?.first()?.field)
        assertEquals(
            "errorCode.validation.name.minLength",
            result.body?.errors?.first()?.translationKey
        )
    }
    @Test
    fun `size validation above maximum returns max length translation key`() {
        val handler = GlobalExceptionHandler()

        val request = TestRequest(name = "abcdef")
        val bindingResult = BeanPropertyBindingResult(request, "request")

        val validator = Validation.buildDefaultValidatorFactory().validator
        SpringValidatorAdapter(validator).validate(request, bindingResult)

        val exception = mock(MethodArgumentNotValidException::class.java)
        `when`(exception.bindingResult).thenReturn(bindingResult)

        val result = handler.handleValidationException(exception)

        assertEquals(HttpStatus.BAD_REQUEST, result.statusCode)
        assertEquals("VALIDATION_FAILED", result.body?.code)
        assertEquals("name", result.body?.errors?.first()?.field)
        assertEquals(
            "errorCode.validation.name.maxLength",
            result.body?.errors?.first()?.translationKey
        )
    }

}