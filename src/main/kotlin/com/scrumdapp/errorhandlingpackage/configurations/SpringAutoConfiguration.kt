package com.scrumdapp.errorhandlingpackage.configurations

import com.scrumdapp.errorhandlingpackage.ApplicationExceptionLogger
import com.scrumdapp.errorhandlingpackage.mvcexceptions.MvcHandlerExceptionResolver
import com.scrumdapp.errorhandlingpackage.mvcexceptions.MvcExceptionConverter
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import tools.jackson.databind.ObjectMapper

@AutoConfiguration
class SpringAutoConfiguration {

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