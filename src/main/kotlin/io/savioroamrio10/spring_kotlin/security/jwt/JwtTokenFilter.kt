package io.savioroamrio10.spring_kotlin.security.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean

class JwtTokenFilter(
    private val tokenProvider: JwtTokenProvider
) : GenericFilterBean() {

    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain
    ) {

        val token =
            tokenProvider.resolveToken(
                request as HttpServletRequest
            )

        println("TOKEN RECEBIDO: $token")

        if (
            !token.isNullOrBlank() &&
            tokenProvider.validateToken(token)
        ) {

            val authentication =
                tokenProvider.getAuthentication(token)

            SecurityContextHolder
                .getContext()
                .authentication = authentication

            println(
                "USUÁRIO AUTENTICADO: ${authentication.name}"
            )
        }

        chain.doFilter(request, response)
    }
}