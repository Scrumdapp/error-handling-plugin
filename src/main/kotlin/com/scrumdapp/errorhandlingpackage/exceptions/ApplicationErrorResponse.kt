package com.scrumdapp.errorhandlingpackage.exceptions

data class ApplicationErrorResponse(
    val status: Int,
    val code: String?,
    val titleTranslationKey: String,
    val descriptionTranslationKey: String,
)


