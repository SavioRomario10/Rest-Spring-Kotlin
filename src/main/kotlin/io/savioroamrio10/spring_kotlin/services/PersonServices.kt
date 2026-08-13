package io.savioroamrio10.spring_kotlin.services

import kotlin.collections.List
import java.util.Arrays
import java.util.ArrayList
import java.util.logging.Logger

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import io.savioroamrio10.spring_kotlin.mapper.DozerMapper
import io.savioroamrio10.spring_kotlin.mapper.custom.PersonMapper

import io.savioroamrio10.spring_kotlin.repository.PersonRepository
import io.savioroamrio10.spring_kotlin.exception.ResourceNotFoundException

import io.savioroamrio10.spring_kotlin.model.Person
import io.savioroamrio10.spring_kotlin.data.vo.v1.PersonVO
import io.savioroamrio10.spring_kotlin.data.vo.v2.PersonVO as PersonVOV2

@Service
class PersonServices{

  @Autowired
  private lateinit var repository: PersonRepository

  @Autowired
  private lateinit var personMapper: PersonMapper
  
  private val logger = Logger.getLogger(PersonServices::class.java.name)

  fun findAll(): List<PersonVO>{

    logger.info("Finding all people!")
    val persons = repository.findAll()

    return DozerMapper.parseListObjects(persons, PersonVO::class.java)
  }

  fun findById(id: Long): PersonVO{

    val entity = repository.findById(id).orElseThrow{
      ResourceNotFoundException("No records found for this ID!")
    }
    logger.info("Finding one person! " + entity.toString())

    return DozerMapper.parseObject(entity, PersonVO::class.java)
  }

  fun create(person: PersonVO): PersonVO{

    var enttity: Person = DozerMapper.parseObject(person, Person::class.java)

    val personSalva = repository.save(enttity)

    logger.info("Creating one person! " + personSalva.toString())
    
    return DozerMapper.parseObject(personSalva, PersonVO::class.java)
  }

  fun createV2(person: PersonVOV2): PersonVOV2{

    var enttity: Person = personMapper.mapVOToEntity(person)

    val personSalva = repository.save(enttity)

    logger.info("Creating one person! " + personSalva.toString())
    
    return personMapper.mapEntityToVO(personSalva)
  }

  fun update(id: Long, person: PersonVO): PersonVO{

    val entity = repository.findById(id).orElseThrow{
      ResourceNotFoundException("No records found for this ID!")
    }

    entity.firstName = person.firstName
    entity.lastName = person.lastName
    entity.address = person.address
    entity.gender = person.gender

    logger.info("Updating one person! " + entity.toString())
    
    
    return DozerMapper.parseObject(repository.save(entity), PersonVO::class.java)
  }

  fun delete(id: Long){

    val entity = repository.findById(id).orElseThrow{
      ResourceNotFoundException("No records found for this ID!")
    }

    repository.delete(entity)

    logger.info("Deleting one person!")
  }
}