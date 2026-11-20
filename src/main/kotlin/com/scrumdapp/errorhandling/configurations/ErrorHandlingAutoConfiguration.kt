package com.scrumdapp.errorhandling.configurations

import com.scrumdapp.errorhandling.logging.ApplicationExceptionLogger
import com.scrumdapp.errorhandling.mvc.MvcHandlerExceptionResolver
import com.scrumdapp.errorhandling.mvc.MvcExceptionConverter
import com.scrumdapp.errorhandling.security.CustomAccessDeniedHandler
import com.scrumdapp.errorhandling.security.CustomAuthenticationEntrypoint
import com.scrumdapp.errorhandling.security.SecurityExceptionResolver
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import tools.jackson.databind.ObjectMapper

@AutoConfiguration
class ErrorHandlingAutoConfiguration {

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
    fun securityExceptionResolver(logger: ApplicationExceptionLogger, objectMapper: ObjectMapper = ObjectMapper()): SecurityExceptionResolver {
        return SecurityExceptionResolver(logger, objectMapper)
    }

    @Bean
    @ConditionalOnMissingBean
    fun exceptionHandlerMvcConfigurator(resolver: MvcHandlerExceptionResolver): MvcConfiguration {
        return MvcConfiguration(resolver)
    }

    @Bean
    @ConditionalOnMissingBean
    fun customAuthenticationEntrypoint(resolver: SecurityExceptionResolver): CustomAuthenticationEntrypoint {
        return CustomAuthenticationEntrypoint(resolver)
    }

    @Bean
    @ConditionalOnMissingBean
    fun customAccessDeniedHandler(resolver: SecurityExceptionResolver): CustomAccessDeniedHandler {
        return CustomAccessDeniedHandler(resolver)
    }
}