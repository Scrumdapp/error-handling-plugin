package com.scrumdapp.errorhandlingpackage

import com.scrumdapp.errorhandlingpackage.exceptions.ApplicationException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.event.Level
import org.springframework.stereotype.Component

@Component
class ApplicationExceptionLogger(
    private val logger: Logger = LoggerFactory.getLogger(ApplicationExceptionLogger::class.java)
) {
    fun logException(ex: ApplicationException) {
        if (!ex.enableLogging) return

        //TODO("figure out better logging format")
        logger.atLevel(ex.logLevel).log(ex.message, if (ex.logLevel.toInt() >= Level.WARN.toInt()) ex else null)
    }
}