package com.scrumdapp.errorhandling

import com.scrumdapp.errorhandling.handlers.CustomHandlerExceptionResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class ExceptionhandlerMvcConfig(
    private val customResolver: CustomHandlerExceptionResolver
): WebMvcConfigurer {

    override fun extendHandlerExceptionResolvers(resolvers: MutableList<HandlerExceptionResolver>) {
        resolvers.add(0, customResolver)
    }

}