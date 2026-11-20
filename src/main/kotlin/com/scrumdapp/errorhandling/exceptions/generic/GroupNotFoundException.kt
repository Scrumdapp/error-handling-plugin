package com.scrumdapp.errorhandling.exceptions.generic

import com.scrumdapp.errorhandling.exceptions.ApplicationException
import org.slf4j.event.Level
import org.springframework.http.HttpStatus

class GroupNotFoundException(
    cause: Throwable? = null,
    logLevel: Level = Level.DEBUG
): ApplicationException(
    HttpStatus.NOT_FOUND,
    "GROUP_NOT_FOUND",
    "errors.group.notFound.title",
    "errors.group.notFound.description",
    logLevel,
    cause
)