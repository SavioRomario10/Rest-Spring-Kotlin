package io.savioroamrio10.spring_kotlin.security.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT

import io.savioroamrio10.spring_kotlin.data.vo.v1.TokenVO
import io.savioroamrio10.spring_kotlin.exception.InvalidJwtException

import jakarta.annotation.PostConstruct
import jakarta.servlet.http.HttpServletRequest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

import java.util.Base64
import java.util.Date

@Service
class JwtTokenProvider {

  @Value("\${security.jwt.token.secret-key:secret}")
  private var secretKey = "secret"

  @Value("\${security.jwt.token.expire-length:3600000}")
  private var validityInMilliseconds: Long = 3600000

  @Autowired
  private lateinit var userDetailsService: UserDetailsService

  private lateinit var algorithm: Algorithm

  @PostConstruct
  protected fun init() {
    secretKey =
      Base64.getEncoder().encodeToString(secretKey.toByteArray())

    algorithm = Algorithm.HMAC256(secretKey.toByteArray())
  }

  fun createAccessToken(username: String, roles: List<String?>): TokenVO {

    val now = Date()
    val validity = Date(now.time + validityInMilliseconds)

    val accessToken =
      getAccessToken(username, roles, now, validity)

    val refreshToken =
      getRefreshToken(username, roles, now)

    return TokenVO(
      username = username,
      authenticated = true,
      created = now,
      expiration = validity,
      accessToken = accessToken,
      refreshToken = refreshToken
    )
  }

  fun refreshToken( refreshToken: String ): TokenVO {

    val token: Striong = ""

    if( refreshToken.contains("Bearer ") ) token = refreshToken.substring("Bearer ".length)
    
    val verifier: JWTVerifier = JWT.require(algorithm).build()

    val decodedJWT = verifier.verify(token)

    val userName: String = decodeJWT.subject

    val roles: List<String?> = decodedJWT.getClaim("roles").asList(String::class.java)

    return createAccessToken(userName, roles)
  }

  private fun getAccessToken( username: String, roles: List<String?>, now: Date, validity: Date ): String {

    val issuerUrl =
      ServletUriComponentsBuilder
        .fromCurrentContextPath()
        .build()
        .toUriString()

    return JWT.create()
      .withClaim("roles", roles)
      .withIssuedAt(now)
      .withExpiresAt(validity)
      .withIssuer(issuerUrl)
      .withSubject(username)
      .sign(algorithm)
  }

  private fun getRefreshToken( username: String, roles: List<String?>, now: Date ): String {

    val validityRefreshToken =
      Date(now.time + validityInMilliseconds * 3)

    return JWT.create()
      .withClaim("roles", roles)
      .withExpiresAt(validityRefreshToken)
      .withSubject(username)
      .sign(algorithm)
  }

  fun getAuthentication( token: String ): Authentication {

    val decodedJWT = decodedToken(token)

    val userDetails: UserDetails =
      userDetailsService.loadUserByUsername(
        decodedJWT.subject
      )

    return UsernamePasswordAuthenticationToken( userDetails, "", userDetails.authorities)
  }

  fun decodedToken(token: String ): DecodedJWT {

    val verifier: JWTVerifier =
      JWT.require(algorithm)
        .build()

    return verifier.verify(token)
  }

  fun resolveToken( req: HttpServletRequest ): String? {

    val bearerToken =
      req.getHeader("Authorization")

    return if (
      !bearerToken.isNullOrBlank() &&
      bearerToken.startsWith("Bearer ")
    ) {
      bearerToken.substring(7)
    } else {
      null
    }
  }

  fun validateToken( token: String ): Boolean {

    return try {

      val decodedJWT = decodedToken(token)

      !decodedJWT.expiresAt.before(Date())

    } catch (e: JWTVerificationException) {

      throw InvalidJwtException( "Expired or invalid JWT token!" )
    }
  }
}