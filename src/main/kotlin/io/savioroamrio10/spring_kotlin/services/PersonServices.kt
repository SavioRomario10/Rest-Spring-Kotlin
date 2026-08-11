package io.savioroamrio10.spring_kotlin.services

import kotlin.collections.List
import java.util.Arrays
import java.util.ArrayList
import java.util.logging.Logger

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import io.savioroamrio10.spring_kotlin.repository.PersonRepository
import io.savioroamrio10.spring_kotlin.exception.ResourceNotFoundException
import io.savioroamrio10.spring_kotlin.model.Person

@Service
class PersonServices{

  @Autowired
  private lateinit var repository: PersonRepository
  
  private val logger = Logger.getLogger(PersonServices::class.java.name)

  fun findAll(): List<Person>{

    logger.info("Finding all people!")
    return repository.findAll()
  }

  fun findById(id: Long): Person{

    val entity = repository.findById(id).orElseThrow{
      ResourceNotFoundException("No records found for this ID!")
    }
    logger.info("Finding one person! " + entity.toString())

    return entity
  }

  fun create(person: Person): Person{

    val personSalva = repository.save(person)

    logger.info("Creating one person! " + personSalva.toString())
    return personSalva
  }

  fun update(id: Long, person: Person): Person{

    val entity = repository.findById(id).orElseThrow{
      ResourceNotFoundException("No records found for this ID!")
    }

    entity.firstName = person.firstName
    entity.lastName = person.lastName
    entity.address = person.address
    entity.gender = person.gender

    logger.info("Updating one person! " + entity.toString())
    return repository.save(entity)
  }

  fun delete(id: Long){

    val entity = repository.findById(id).orElseThrow{
      ResourceNotFoundException("No records found for this ID!")
    }

    repository.delete(entity)

    logger.info("Deleting one person!")
  }
}