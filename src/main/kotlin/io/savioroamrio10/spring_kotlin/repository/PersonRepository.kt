package io.savioroamrio10.spring_kotlin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import io.savioroamrio10.spring_kotlin.model.Person

@Repository
interface PersonRepository : JpaRepository<Person, Long> {
}