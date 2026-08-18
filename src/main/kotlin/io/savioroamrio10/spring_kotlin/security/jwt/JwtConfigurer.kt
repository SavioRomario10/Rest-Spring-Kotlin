package io.savioroamrio10.spring_kotlin.security.jwt

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class JwtConfigurer(@field:Autowired private val tokenProvider: JwtTokenProvider): SecurityConfigurerAdapter<DefaultSecurityFilterChain?, HttpSecurity>(){

  override fun configure(http: HttpSecurity?){
    
    val tokenFilter = JwtTOkenFilter(tokenProvider)

    http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter::class.java)
  }
}