package io.savioroamrio10.spring_kotlin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

import io.savioroamrio10.spring_kotlin.model.Person

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Page

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.jpa.repository.Modifying

@Repository
interface PersonRepository : JpaRepository<Person, Long> {

  @Modifying
  @Query("UPDATE Person p SET p.enabled = false WHERE p.id = :id")
  fun desablePerson(@Param("id") id: Long)

  @Query("SELECT p FROM Person p WHERE p.firstName LIKE LOWER(CONCAT('%', :firstName, '%'))")
  fun findPersonByName(@Param("firstName") firstName: String, pageable: Pageable): Page<Person>
}