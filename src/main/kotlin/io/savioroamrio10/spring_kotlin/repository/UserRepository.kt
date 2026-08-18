package io.savioroamrio10.spring_kotlin.repository

import io.savioroamrio10.spring_kotlin.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User?, Long?>{

  @Query("SELECT u FROM User u WHERE u.userName = :username")
  fun findByUsername(@Param("username") username: String?): User?
}