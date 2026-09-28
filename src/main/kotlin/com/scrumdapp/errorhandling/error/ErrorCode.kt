package com.scrumdapp.errorhandling.error

enum class ErrorCode(
    val code: String,
    val errorTitleKey: String,
    val errorDescriptionKey: String
) {
    BAD_REQUEST(
        "BAD_REQUEST",
        "errorCode.generic.badRequest.title",
        "errorCode.generic.badRequest.description"
    ),

    VALIDATION_FAILED(
        "VALIDATION_FAILED",
        "errorCode.validation.failed.title",
        "errorCode.validation.failed.description"
    ),

    INVITE_EXPIRED(
        "INVITE_EXPIRED",
        "errorCode.invite.expired.title",
        "errorCode.invite.expired.description"
    ),

    CHECKPOINT_EXPIRED(
        "CHECKPOINT_EXPIRED",
        "errorCode.checkpoint.expired.title",
        "errorCode.checkpoint.expired.description"
    ),

    AUTHENTICATION_REQUIRED(
        "AUTHENTICATION_REQUIRED",
        "errorCode.generic.authenticationRequired.title",
        "errorCode.generic.authenticationRequired.description"
    ),

    INVALID_TOKEN(
        "INVALID_TOKEN",
        "errorCode.generic.invalidToken.title",
        "errorCode.generic.invalidToken.description"
    ),

    ACCESS_DENIED(
        "ACCESS_DENIED",
        "errorCode.generic.accessDenied.title",
        "errorCode.generic.accessDenied.description"
    ),

    USER_NOT_FOUND(
        "USER_NOT_FOUND",
        "errorCode.user.notFound.title",
        "errorCode.user.notFound.description"
    ),

    GROUP_NOT_FOUND(
        "GROUP_NOT_FOUND",
        "errorCode.group.notFound.title",
        "errorCode.group.notFound.description"
    ),

    INVITE_NOT_FOUND(
        "INVITE_NOT_FOUND",
        "errorCode.invite.notFound.title",
        "errorCode.invite.notFound.description"
    ),

    INTERNAL_ERROR(
        "INTERNAL_ERROR",
        "errorCode.generic.internal.title",
        "errorCode.generic.internal.description"
    ),

    SERVICE_UNAVAILABLE(
        "SERVICE_UNAVAILABLE",
        "errorCode.generic.serviceUnavailable.title",
        "errorCode.generic.serviceUnavailable.description"
    )
}