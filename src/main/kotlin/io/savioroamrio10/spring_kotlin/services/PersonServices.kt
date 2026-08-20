package io.savioroamrio10.spring_kotlin.services

import kotlin.collections.List
import java.util.Arrays
import java.util.ArrayList
import java.util.logging.Logger

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel

import io.savioroamrio10.spring_kotlin.controller.PersonController
import io.savioroamrio10.spring_kotlin.mapper.DozerMapper
import io.savioroamrio10.spring_kotlin.mapper.custom.PersonMapper
import io.savioroamrio10.spring_kotlin.repository.PersonRepository
import io.savioroamrio10.spring_kotlin.exception.ResourceNotFoundException
import io.savioroamrio10.spring_kotlin.model.Person
import io.savioroamrio10.spring_kotlin.data.vo.v1.PersonVO
import io.savioroamrio10.spring_kotlin.data.vo.v2.PersonVO as PersonVOV2

import jakarta.transaction.Transactional

@Service
class PersonServices{

  @Autowired
  private lateinit var repository: PersonRepository

  @Autowired
  private lateinit var personMapper: PersonMapper
  
  private val logger = Logger.getLogger(PersonServices::class.java.name)
 
  fun findAll(pageable: Pageable): PagedModel<EntityModel<PersonVO>> {

    logger.info("Finding all people!")

    val entities = repository.findAll(pageable)

    val vos = entities.map { it -> DozerMapper.parseObject(it, PersonVO::class.java) }

    vos.map { it -> it.add(
      linkTo(methodOn(PersonController::class.java).findById(it.key!!)).withSelfRel()
    ) }

    return vos
  }

  fun findById(id: Long): PersonVO {

    val entity = repository.findById(id).orElseThrow {
      ResourceNotFoundException("No records found for this ID!")
    }

    logger.info("Finding one person! $entity")

    val personVO = DozerMapper.parseObject(entity, PersonVO::class.java)

    personVO.key = entity.id

    personVO.add(
    linkTo(PersonController::class.java)
      .slash(personVO.key)
      .withSelfRel()
    )

    return personVO
  }

  @Transactional
  fun disablePerson(id: Long): PersonVO {

    val entity = repository.findById(id).orElseThrow {
      ResourceNotFoundException("No records found for this ID!")
    }

    repository.desablePerson(id)

    logger.info("Disabling one person! $entity")

    val personVO = DozerMapper.parseObject(entity, PersonVO::class.java)

    personVO.key = entity.id

    personVO.add(
    linkTo(PersonController::class.java)
      .slash(personVO.key)
      .withSelfRel()
    )

    return personVO
  }

  fun create(person: PersonVO): PersonVO{

    var enttity: Person = DozerMapper.parseObject(person, Person::class.java)

    val personSalva = repository.save(enttity)

    logger.info("Creating one person! " + personSalva.toString())
    
    val personVO = DozerMapper.parseObject(personSalva, PersonVO::class.java)
    personVO.key = personSalva.id

    personVO.add(
    linkTo(PersonController::class.java)
      .slash(personVO.key)
      .withSelfRel()
    )

    return personVO
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
    
    val personVO = DozerMapper.parseObject(repository.save(entity), PersonVO::class.java)

    personVO.key = entity.id

    personVO.add(
    linkTo(PersonController::class.java)
      .slash(personVO.key)
      .withSelfRel()
    )

    return personVO
  }

  fun delete(id: Long){

    val entity = repository.findById(id).orElseThrow{
      ResourceNotFoundException("No records found for this ID!")
    }

    repository.delete(entity)

    logger.info("Deleting one person!")
  }
}