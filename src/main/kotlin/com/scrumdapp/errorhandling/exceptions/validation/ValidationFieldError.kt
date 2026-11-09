package com.scrumdapp.errorhandling.exceptions.validation

data class ValidationFieldError(
    val field: String,
    val translationKey: String,
)