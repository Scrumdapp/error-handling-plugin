package com.scrumdapp.errorhandlingpackage.exceptions.validation

data class ValidationFieldError(
    val field: String,
    val translationKey: String,
)