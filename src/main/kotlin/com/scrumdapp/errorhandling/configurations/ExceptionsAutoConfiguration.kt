package com.scrumdapp.errorhandling.configurations

import com.scrumdapp.errorhandling.ApplicationExceptionLogger
import com.scrumdapp.errorhandling.mvcexceptions.MvcHandlerExceptionResolver
import com.scrumdapp.errorhandling.mvcexceptions.MvcExceptionConverter
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import tools.jackson.databind.ObjectMapper

@AutoConfiguration
class ExceptionsAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    fun mvcExceptionConverter(): MvcExceptionConverter {
        return MvcExceptionConverter()
    }

    @Bean
    @ConditionalOnMissingBean
    fun exceptionLogger(): ApplicationExceptionLogger {
        return ApplicationExceptionLogger()
    }

    @Bean
    @ConditionalOnMissingBean
    fun mvcHandlerExceptionResolver(exceptionConverter: MvcExceptionConverter, applicationExceptionLogger: ApplicationExceptionLogger, objectMapper: ObjectMapper = ObjectMapper()): MvcHandlerExceptionResolver {
        return MvcHandlerExceptionResolver(exceptionConverter, applicationExceptionLogger, objectMapper)
    }

    @Bean
    @ConditionalOnMissingBean
    fun exceptionHandlerMvcConfigurator(resolver: MvcHandlerExceptionResolver): MvcConfiguration {
        return MvcConfiguration(resolver)
    }
}