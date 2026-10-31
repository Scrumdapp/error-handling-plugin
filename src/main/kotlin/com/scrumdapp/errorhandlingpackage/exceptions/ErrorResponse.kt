package com.scrumdapp.errorhandlingpackage.exceptions

import com.scrumdapp.errorhandlingpackage.exceptions.validation.ValidationFieldError

data class ErrorResponse(
    val status: Int,
    val code: String?,
    val titleTranslationKey: String,
    val descriptionTranslationKey: String,
)

data class ValidationErrorResponse(
    val status: Int,
    val code: String?,
    val titleTranslationKey: String,
    val descriptionTranslationKey: String,
    val errors: List<ValidationFieldError> = emptyList()
)


