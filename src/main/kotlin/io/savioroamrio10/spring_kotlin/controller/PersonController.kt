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
import org.springframework.http.MediaType

import io.savioroamrio10.spring_kotlin.model.Person
import io.savioroamrio10.spring_kotlin.services.PersonServices

@RestController
@RequestMapping("/person")
class PersonController{

  @Autowired
  private lateinit var service: PersonServices

  companion object {
    private const val TYPE = MediaType.APPLICATION_JSON_VALUE
  }
  
  @GetMapping(produces = [TYPE])
  fun findAll(): List<Person>{
    return service.findAll()
  }

  @GetMapping(value = ["/{id}"], produces = [TYPE])
  fun findById(@PathVariable(value = "id") id: Long): Person{
    return service.findById(id)
  }

  @PostMapping(consumes = [TYPE], produces = [TYPE])
  fun create(@RequestBody person: Person): Person{
    return service.create(person)
  }

  @PutMapping(value = ["/{id}"],consumes = [TYPE], produces = [TYPE])
  fun update(@PathVariable(value = "id") id: Long, @RequestBody person: Person): Person{
    return service.update(id, person)
  }

  @DeleteMapping(value = ["/{id}"])
  fun delete(@PathVariable(value = "id") id: Long){
    service.delete(id)
  }
}