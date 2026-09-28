package com.scrumdapp.errorhandling.dto

data class ValidationError(
    val field: String,
    val translationKey: String
)