package com.scrumdapp.errorhandling.errorResponses

data class ValidationErrorResponse(
    val field: String,
    val translationKey: String
)