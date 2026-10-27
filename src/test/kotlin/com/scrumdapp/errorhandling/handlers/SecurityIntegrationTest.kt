
package com.scrumdapp.errorhandling.handlers

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootTest(classes = [SecurityIntegrationTest.TestApplication::class])
@AutoConfigureMockMvc
class SecurityIntegrationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var applicationContext: ApplicationContext

    @Test
    fun `unauthenticated request returns 401`() {
        mockMvc.get("/test/secure")
            .andExpect {
                status { isUnauthorized() }
                jsonPath("$.status") {
                    value(401)
                }
                jsonPath("$.code") {
                    value("AUTHENTICATION_REQUIRED")
                }
                jsonPath("$.errorTitleKey") {
                    value("errorCode.generic.authenticationRequired.title")
                }
                jsonPath("$.errorDescriptionKey") {
                    value("errorCode.generic.authenticationRequired.description")
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
            jsonPath("$.status") {
                value(403)
            }
            jsonPath("$.code") {
                value("ACCESS_DENIED")
            }
            jsonPath("$.errorTitleKey") {
                value("errorCode.generic.accessDenied.title")
            }
            jsonPath("$.errorDescriptionKey") {
                value("errorCode.generic.accessDenied.description")
            }
        }
    }

    @Test
    fun `application security configuration is retained`() {
        assertEquals(1, applicationContext.getBeansOfType(SecurityFilterChain::class.java).size)

        mockMvc.get("/test/public")
            .andExpect {
                status { isOk() }
            }
    }

    @Configuration
    @EnableAutoConfiguration
    class TestApplication {

        @Bean
        fun securityFilterChain(
            http: HttpSecurity,
            authenticationEntryPoint: SecurityAuthenticationEntryPoint,
            accessDeniedHandler: SecurityAccessDeniedHandler
        ): SecurityFilterChain {
            return http
                .authorizeHttpRequests {
                    it.requestMatchers("/test/admin").hasRole("ADMIN")
                    it.requestMatchers("/test/public").permitAll()
                    it.anyRequest().authenticated()
                }
                .exceptionHandling {
                    it.authenticationEntryPoint(authenticationEntryPoint)
                    it.accessDeniedHandler(accessDeniedHandler)
                }
                .httpBasic {
                    it.authenticationEntryPoint(authenticationEntryPoint)
                }
                .build()
        }

        @Bean
        fun testController() = TestController()
    }

    @RestController
    class TestController {
        @GetMapping("/test/public")
        fun public() = "OK"

        @GetMapping("/test/secure")
        fun secure() = "OK"

        @GetMapping("/test/admin")
        fun admin() = "OK"
    }
}
