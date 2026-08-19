package io.savioroamrio10.spring_kotlin.security.jwt

import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class JwtConfigurer( private val tokenProvider: JwtTokenProvider ) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

  override fun configure(http: HttpSecurity) {

    val tokenFilter = JwtTokenFilter(tokenProvider)

    http.addFilterBefore( tokenFilter, UsernamePasswordAuthenticationFilter::class.java)
  }
}