package com.scrumdapp.errorhandling.errorResponses

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include

@JsonInclude(Include.NON_NULL)
data class ErrorResponse(
    val status: Int,
    val code: String,
    val errorTitleKey: String,
    val errorDescriptionKey: String,
    val errors: List<ValidationErrorResponse>? = null
)