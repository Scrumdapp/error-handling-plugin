package com.scrumdapp.errorhandling.dto


data class ErrorResponse(
    val status: Int,
    val code: String,
    val errorTitleKey: String,
    val errorDescriptionKey: String,
    val errors: List<ValidationError>? = null
)