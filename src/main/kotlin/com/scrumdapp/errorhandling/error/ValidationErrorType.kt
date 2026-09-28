package com.scrumdapp.errorhandling.error

import jakarta.validation.ConstraintViolation
import jakarta.validation.constraints.Size
import org.springframework.validation.FieldError

enum class ValidationErrorType(
    val key: String
) {
    NOT_BLANK("required"),
    NOT_NULL("required"),
    EMAIL("invalid"),
    MIN_LENGTH("minLength"),
    MAX_LENGTH("maxLength"),
    MIN("tooSmall"),
    MAX("tooLarge"),
    INVALID("invalid");

    companion object {

        fun fromFieldError(fieldError: FieldError): ValidationErrorType {

            if (fieldError.contains(ConstraintViolation::class.java)) {
                val violation = fieldError.unwrap(ConstraintViolation::class.java)
                val annotation =
                    violation.constraintDescriptor.annotation

                if (annotation is Size) {
                    val value = fieldError.rejectedValue

                    if (value is CharSequence) {
                        return when {
                            value.length < annotation.min -> MIN_LENGTH
                            value.length > annotation.max -> MAX_LENGTH
                            else -> INVALID
                        }
                    }
                }

                return when (annotation.annotationClass.simpleName) {
                    "NotBlank" -> NOT_BLANK
                    "NotNull" -> NOT_NULL
                    "Email" -> EMAIL
                    "Min" -> MIN
                    "Max" -> MAX
                    else -> INVALID
                }
            }

            return INVALID
        }
    }
}