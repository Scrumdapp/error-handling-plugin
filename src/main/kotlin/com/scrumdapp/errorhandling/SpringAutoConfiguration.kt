package com.scrumdapp.errorhandling

import com.scrumdapp.errorhandling.handlers.CustomHandlerExceptionResolver
import com.scrumdapp.errorhandling.handlers.ExceptionHandler
import com.scrumdapp.errorhandling.handlers.ExceptionHandlerFilter
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import tools.jackson.databind.ObjectMapper

@AutoConfiguration
class SpringAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    fun exceptionHandler(objectMapper: ObjectMapper): ExceptionHandler {
        return ExceptionHandler(objectMapper)
    }

    @Bean
    fun exceptionFilter(handler: ExceptionHandler): ExceptionHandlerFilter {
        return ExceptionHandlerFilter(handler)
    }

    @Bean
    @ConditionalOnMissingBean
    fun exceptionHandlerResolver(handler: ExceptionHandler): CustomHandlerExceptionResolver {
        return CustomHandlerExceptionResolver( handler)
    }

    @Bean
    fun exceptionhandlerMvcConfigurator(resolver: CustomHandlerExceptionResolver): ExceptionhandlerMvcConfig {
        return ExceptionhandlerMvcConfig(resolver)
    }
}