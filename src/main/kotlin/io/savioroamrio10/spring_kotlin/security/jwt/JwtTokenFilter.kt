package io.savioroamrio10.spring_kotlin.security.jwt

import javax.servlet.*
import javax.servlet.http.*
import org.springframework.web.filter.GenericFilterBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder

class JwtTOkenFilter(@field:Autowired private val tokenProvider: JwtTokenProvider): GenericFilterBean(){

  override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain){

    val token = tokenProvider.resolveToken(request as HttpServiletRequest)

    if(!token.isNullOrBlank && tokenProvider.validateToken(token)){
      val authentication = tokenProvider.getAuthentication(token)
      SecurityContextHolder.getContext().authentication = authentication
    }

    chain.doFilter(request, response)
  }
}