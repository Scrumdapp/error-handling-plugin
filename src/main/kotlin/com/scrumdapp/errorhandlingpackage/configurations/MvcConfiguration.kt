package com.scrumdapp.errorhandlingpackage.configurations

import com.scrumdapp.errorhandlingpackage.interceptors.ApplicationHandlerExceptionResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class MvcConfiguration(
    private val resolver: ApplicationHandlerExceptionResolver
): WebMvcConfigurer {

    override fun extendHandlerExceptionResolvers(resolvers: MutableList<HandlerExceptionResolver>) {
        resolvers.add(0, resolver)
    }
}