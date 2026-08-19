package io.savioroamrio10.spring_kotlin.services

import io.savioroamrio10.spring_kotlin.data.vo.v1.AccountCredentialsVO
import io.savioroamrio10.spring_kotlin.data.vo.v1.TokenVO
import io.savioroamrio10.spring_kotlin.repository.UserRepository
import io.savioroamrio10.spring_kotlin.security.jwt.JwtTokenProvider

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

import java.util.logging.Logger

@Service
class AuthService {

  @Autowired
  private lateinit var repository: UserRepository

  @Autowired
  private lateinit var authenticationManager: AuthenticationManager

  @Autowired
  private lateinit var tokenProvider: JwtTokenProvider

  private val logger = Logger.getLogger(AuthService::class.java.name)

  fun signin(data: AccountCredentialsVO): ResponseEntity<*> {

    logger.info("Signing in...")

    return try {

      val username = data.username
      val password = data.password

      authenticationManager.authenticate(UsernamePasswordAuthenticationToken( username, password))

      val user = repository.findByUsername(username)
      logger.info("User found: $user")

      val tokenResponse: TokenVO =
        if (user != null) {

          tokenProvider.createAccessToken( user.userName ?: "", user.roles)

        } else { throw Exception( "User $username not found!" )}

      ResponseEntity.ok(tokenResponse)

    } catch (e: AuthenticationException) {

      throw BadCredentialsException( "Invalid username/password supplied" )
    }
  }
}