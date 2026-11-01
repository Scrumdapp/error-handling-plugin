package com.scrumdapp.errorhandlingpackage.securityhandlers

import org.springframework.security.config.annotation.web.builders.HttpSecurity

fun HttpSecurity.useExceptionHandling(
    authenticationEntrypoint: CustomAuthenticationEntrypoint,
    accessDeniedHandler: CustomAccessDeniedHandler
): HttpSecurity {
    return this
        .exceptionHandling {
            it.authenticationEntryPoint(authenticationEntrypoint)
            it.accessDeniedHandler(accessDeniedHandler)
        }
}