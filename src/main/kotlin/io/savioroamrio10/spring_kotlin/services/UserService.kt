package io.savioroamrio10.spring_kotlin.services

import io.savioroamrio10.spring_kotlin.repository.UserRepository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

import java.util.logging.Logger

@Service
class UserService(@field:Autowired var repository: UserRepository) : UserDetailsService{

  private val logger = Logger.getLogger(UserServvice::class.java.name)

  override fun loadUserByUsername(username: String?): UserDetails? {
    logger.info("Finding one user! $username")

    val user = repository.findByUsername(username)

    return user ?: throw Exception("User $username not found!")
  }
}