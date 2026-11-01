package com.scrumdapp.errorhandlingpackage.exceptions.validation

data class ValidationErrorResponse(
    val status: Int,
    val code: String?,
    val titleTranslationKey: String,
    val descriptionTranslationKey: String,
    val errors: List<ValidationFieldError> = emptyList()
)