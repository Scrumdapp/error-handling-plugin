package com.scrumdapp.errorhandling.error

import jakarta.validation.ConstraintViolation
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
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

        private val annotationTypes = mapOf(
            NotBlank::class.java to NOT_BLANK,
            NotNull::class.java to NOT_NULL,
            Email::class.java to EMAIL,
            Min::class.java to MIN,
            Max::class.java to MAX,
        )

        fun fromFieldError(fieldError: FieldError): ValidationErrorType {

            if (!fieldError.contains(ConstraintViolation::class.java)) {
                return INVALID
            }

            val violation = fieldError.unwrap(ConstraintViolation::class.java)
            return when (val annotation = violation.constraintDescriptor.annotation) {
                is Size -> fromSize(annotation, fieldError) ?: INVALID
                else -> annotationTypes[annotation.annotationClass.java] ?: INVALID
            }
        }

        private fun fromSize(annotation: Size, fieldError: FieldError): ValidationErrorType? {
            return (fieldError.rejectedValue as? CharSequence)?.let {
                when {
                    it.length < annotation.min -> MIN_LENGTH
                    it.length > annotation.max -> MAX_LENGTH
                    else -> INVALID
                }
            }
        }
    }
}