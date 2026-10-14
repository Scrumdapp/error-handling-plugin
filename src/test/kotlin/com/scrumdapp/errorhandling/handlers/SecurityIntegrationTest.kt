
package com.scrumdapp.errorhandling.handlers

import com.scrumdapp.errorhandling.SpringAutoConfiguration
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.test.web.servlet.MockMvc
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.get
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootTest(classes = [SecurityIntegrationTest.TestApplication::class])
@AutoConfigureMockMvc
class SecurityIntegrationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `unauthenticated request returns 401`() {
        mockMvc.get("/test/secure")
            .andExpect {
                status { isUnauthorized() }
                jsonPath("$.code") {
                    value("AUTHENTICATION_REQUIRED")
                }
            }
    }

    @Test
    fun `authenticated user without role returns 403`() {
        mockMvc.get("/test/admin") {
            with(
                org.springframework.security.test.web.servlet.request
                    .SecurityMockMvcRequestPostProcessors.user("test")
            )
        }.andExpect {
            status { isForbidden() }
            jsonPath("$.code") {
                value("ACCESS_DENIED")
            }
        }
    }

    @Configuration
    @EnableAutoConfiguration
    class TestApplication {

        @Bean
        fun securityFilterChain(
            http: HttpSecurity,
            securityExceptionHandler: SecurityExceptionHandler
        ): SecurityFilterChain {
            return http
                .authorizeHttpRequests {
                    it.requestMatchers("/test/admin").hasRole("ADMIN")
                    it.anyRequest().authenticated()
                }
                .exceptionHandling {
                    it.authenticationEntryPoint(securityExceptionHandler)
                    it.accessDeniedHandler(securityExceptionHandler)
                }
                .httpBasic {
                    it.authenticationEntryPoint(securityExceptionHandler)
                }
                .build()
        }

        @Bean
        fun testController() = TestController()
    }

    @RestController
    class TestController {
        @GetMapping("/test/secure")
        fun secure() = "OK"

        @GetMapping("/test/admin")
        fun admin() = "OK"
    }
}
