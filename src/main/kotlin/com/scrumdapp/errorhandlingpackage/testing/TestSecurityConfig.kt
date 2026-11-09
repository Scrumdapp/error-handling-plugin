package com.scrumdapp.errorhandlingpackage.testing

import com.scrumdapp.errorhandlingpackage.securityhandlers.CustomAccessDeniedHandler
import com.scrumdapp.errorhandlingpackage.securityhandlers.CustomAuthenticationEntrypoint
import com.scrumdapp.errorhandlingpackage.securityhandlers.useExceptionHandling
import com.scrumdapp.passportplugin.filters.PassportAuthFilter
import com.scrumdapp.passportplugin.filters.usePassport
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebSecurity
class TestSecurityConfig(
    private val authenticationEntryPoint: CustomAuthenticationEntrypoint,
    private val accessDeniedHandler: CustomAccessDeniedHandler,
    private val passportFilter: PassportAuthFilter
): WebMvcConfigurer {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .usePassport(passportFilter)
            .useExceptionHandling(authenticationEntryPoint, accessDeniedHandler)
            .formLogin(withDefaults())
            .authorizeHttpRequests {
                it.anyRequest().permitAll()
            }

        return http.build()
    }
}