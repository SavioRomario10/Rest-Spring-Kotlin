package io.savioroamrio10.spring_kotlin.config

import io.savioroamrio10.spring_kotlin.security.jwt.JwtTokenFilter
import io.savioroamrio10.spring_kotlin.security.jwt.JwtTokenProvider

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy

import org.springframework.security.crypto.password.DelegatingPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder

import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class SecurityConfig {

    @Autowired
    private lateinit var tokenProvider: JwtTokenProvider

    @Bean
    fun passwordEncoder(): PasswordEncoder {

        val encoders = HashMap<String, PasswordEncoder>()

        encoders["pbkdf2"] =
            Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8()

        val passwordEncoder =
            DelegatingPasswordEncoder("pbkdf2", encoders)

        passwordEncoder.setDefaultPasswordEncoderForMatches(
            Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8()
        )

        return passwordEncoder
    }

    @Bean
    fun authenticationManager(
        authenticationConfiguration: AuthenticationConfiguration
    ): AuthenticationManager {

        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun securityFilterChain(
        http: HttpSecurity
    ): SecurityFilterChain {

        http
            .httpBasic { it.disable() }
            .csrf { it.disable() }
            .cors(Customizer.withDefaults())
            .sessionManagement {
                it.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            }
            .authorizeHttpRequests {

                it.requestMatchers(
                    "/auth/signin",
                    "/auth/refresh/**",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/swagger-ui.html"
                ).permitAll()

                it.requestMatchers("/users")
                    .denyAll()

                it.requestMatchers("/api/**")
                    .authenticated()

                it.anyRequest()
                    .authenticated()
            }

        http.addFilterBefore(
            JwtTokenFilter(tokenProvider),
            UsernamePasswordAuthenticationFilter::class.java
        )

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {

        val configuration = CorsConfiguration()

        configuration.allowedOriginPatterns = listOf("*")
        configuration.allowedMethods = listOf("*")
        configuration.allowedHeaders = listOf("*")
        configuration.allowCredentials = false

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)

        return source
    }
}