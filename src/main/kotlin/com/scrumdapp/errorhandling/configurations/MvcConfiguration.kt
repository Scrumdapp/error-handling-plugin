package com.scrumdapp.errorhandling.configurations

import com.scrumdapp.errorhandling.mvcexceptions.MvcHandlerExceptionResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class MvcConfiguration(
    private val resolver: MvcHandlerExceptionResolver
): WebMvcConfigurer {

    override fun extendHandlerExceptionResolvers(resolvers: MutableList<HandlerExceptionResolver>) {
        resolvers.add(0, resolver)
    }
}