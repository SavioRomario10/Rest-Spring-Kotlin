package io.savioroamrio10.spring_kotlin.security.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import com.auth0.jwt.interfaces.DecodedJWT

import io.savioroamrio10.spring_kotlin.data.vo.v1.TokenVO

import jakarta.annotation.PostConstruct

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

import java.util.Base64
import java.util.Date

@Service
class JwtTokenProvider{

  @Value("\${security.jwt.token.secret-key:secret}")
  private var secretKey = "secret"

  @Value("\${security.jwt.token.expire-length:3600000}")
  private val validityInMilliseconds: Long = 3600000

  @Autowired
  private lateinit var userDetailsService: UserDetailsService

  private lateinit var algorithm: Algorithm

  @PostConstruct
  protected fun init(){
    
    secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
    algorithm = Algorithm.HMAC256(secretKey.toByteArray())
  }
 
  fun createAcessToken(username: String, roles: List<String?>): TokenVO{

    val now = Date()
    val validity = Date(now.time + validityInMilliseconds)
    val accessToken = getAccessToken(username, roles, now, validity)
    val refreshToken = getRefreshToken(username, roles, now)

    return TokenVO(
      username = username,
      authenticated = true,
      accessToken = accessToken,
      refreshToken = refreshToken,
      created = now,
      expiration = validity
    )
  }

  private fun getAccessToken(username: String, roles: List<String?>, now: Date, validity: Date): String{
     
    val issuerURL: String = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()

    return JWT.create()
      .withClaim("roles", roles)
      .withIssuedAt(now)
      .withExpiresAt(validity)
      .withIssuer(issuerURL)
      .withSubject(username)
      .sign(algorithm)
      .trim()
  }

  private fun getRefreshToken(username: String, roles: List<String?>, now: Date): String{

    val validityRefreshToken = Date(now.time + validityInMilliseconds * 3)

    return JWT.create()
      .withClaim("roles", roles)
      .withExpiresAt(validityRefreshToken)
      .withSubject(username)
      .sign(algorithm)
      .trim()
  }

  fun getAuthentication(token: String): Authentication{

    val decodedJWT: DecodedJWT = decodedToken(token)
    val userDetails: UserDetails = userDetailsService.loadUserByUsername(decodedJWT.subject)

    return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
  }

  fun decodedToken(token: String): DecodedJWT{

    val algorithm = Algorithm.HMAC256(secretKey.toByteArray())
    val verifier: JWTVerifier = JWT.require(algorithm).build()

    return verifier.verify(token)
  }

  fun resolveToken(req: HttpServletRequest): String?{

    val bearerToken = req.getHeader("Authorization")

    return if(!bearerToken.isNullOrBlank() && bearerToken.startsWith("Bearer ")){
      bearerToken.substring("Bearer ".length)

    }else null
  }

  fun validateToken(token: String): Boolean{

    val decodeJWT = decodedToken(token)

    try{
      if(decodedJWT.expiresAt.bofore(Date())) false
      return true
    } 
    catch(e: Exception){
      throw InvalidJwtException("Expired or invalid JWT token")
    }
  }
}