package io.savioroamrio10.spring_kotlin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

import io.savioroamrio10.spring_kotlin.model.Person

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.jpa.repository.Modifying

@Repository
interface PersonRepository : JpaRepository<Person, Long> {

  @Modifying
  @Query("UPDATE Person p SET p.enabled = false WHERE p.id = :id")
  fun desablePerson(@Param("id") id: Long)
}