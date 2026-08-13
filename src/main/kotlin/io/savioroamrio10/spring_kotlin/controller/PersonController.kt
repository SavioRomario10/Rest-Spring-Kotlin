package io.savioroamrio10.spring_kotlin.controller

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.http.ResponseEntity
import org.springframework.http.MediaType

import io.savioroamrio10.spring_kotlin.data.vo.v1.PersonVO
import io.savioroamrio10.spring_kotlin.data.vo.v2.PersonVO as PersonVOV2
import io.savioroamrio10.spring_kotlin.services.PersonServices

@RestController
@RequestMapping("/api/person/v1")
class PersonController{

  @Autowired
  private lateinit var service: PersonServices 

  companion object {
    private const val TYPE = MediaType.APPLICATION_JSON_VALUE
  }
  
  @GetMapping(produces = [TYPE])
  fun findAll(): List<PersonVO>{
    return service.findAll()
  }

  @GetMapping(value = ["/{id}"], produces = [TYPE])
  fun findById(@PathVariable(value = "id") id: Long): PersonVO{
    return service.findById(id)
  }

  @PostMapping(consumes = [TYPE], produces = [TYPE])
  fun create(@RequestBody person: PersonVO): PersonVO{
    return service.create(person)
  }

  @PostMapping(value = ["/{id}"], consumes = [TYPE], produces = [TYPE])
  fun createV2(@RequestBody person: PersonVOV2): PersonVOV2{
    return service.createV2(person)
  }

  @PutMapping(value = ["/{id}"],consumes = [TYPE], produces = [TYPE])
  fun update(@PathVariable(value = "id") id: Long, @RequestBody person: PersonVO): PersonVO{
    return service.update(id, person)
  }

  @DeleteMapping(value = ["/{id}"])
  fun delete(@PathVariable(value = "id") id: Long): ResponseEntity<Void>{
    service.delete(id)
    return ResponseEntity.noContent().build()
  }
}