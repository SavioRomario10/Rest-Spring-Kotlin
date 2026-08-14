package io.savioroamrio10.spring_kotlin.controller

import io.savioroamrio10.spring_kotlin.data.vo.v1.PersonVO
import io.savioroamrio10.spring_kotlin.data.vo.v2.PersonVO as PersonVOV2
import io.savioroamrio10.spring_kotlin.services.PersonServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/person/v1")
open class PersonController {

  @Autowired
  private lateinit var service: PersonServices

  companion object {
    const val JSON = MediaType.APPLICATION_JSON_VALUE
    const val XML = MediaType.APPLICATION_XML_VALUE
  }

  @GetMapping(produces = [JSON, XML])
  fun findAll(): List<PersonVO> {
    return service.findAll()
  }

  @GetMapping(value = ["/{id}"], produces = [JSON, XML])
  fun findById(@PathVariable(value = "id") id: Long): PersonVO {
    return service.findById(id)
  }

  @PostMapping(consumes = [JSON, XML], produces = [JSON, XML])
  fun create(@RequestBody person: PersonVO): PersonVO {
    return service.create(person)
  }

  @PostMapping(value = ["/v2"], consumes = [JSON, XML], produces = [JSON, XML]
  )
  fun createV2(@RequestBody person: PersonVOV2): PersonVOV2 {
    return service.createV2(person)
  }

  @PutMapping(value = ["/{id}"], consumes = [JSON, XML], produces = [JSON, XML])
  fun update(@PathVariable(value = "id") id: Long, @RequestBody person: PersonVO): PersonVO {
    return service.update(id, person)
  }

  @DeleteMapping("/{id}")
  fun delete(@PathVariable(value = "id") id: Long): ResponseEntity<Void> {
    service.delete(id)
    return ResponseEntity.noContent().build()
  }
}