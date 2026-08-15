package io.savioroamrio10.spring_kotlin.controller

import io.savioroamrio10.spring_kotlin.data.vo.v1.PersonVO
import io.savioroamrio10.spring_kotlin.data.vo.v2.PersonVO as PersonVOV2
import io.savioroamrio10.spring_kotlin.services.PersonServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema

@RestController
@RequestMapping("/api/person/v1")
@Tag(
  name="People",
  description="Endpoints for Managing People"
)
open class PersonController {

  @Autowired
  private lateinit var service: PersonServices

  companion object {
    const val JSON = MediaType.APPLICATION_JSON_VALUE
    const val XML = MediaType.APPLICATION_XML_VALUE
  }

  @GetMapping(produces = [JSON, XML])
  @Operation(
    summary = "Find all people", 
    description = "Find all people", 
    tags = ["People"],
    responses = [
      ApiResponse(
        description = "Success", 
        responseCode = "200", 
        content = [
          Content(
            array = ArraySchema(schema = Schema(implementation = PersonVO::class))
        )]
      ),
      ApiResponse(description = "Bad Request", responseCode = "400", content = [Content()]),
      ApiResponse(description = "Unauthorized", responseCode = "401", content = [Content()]),
      ApiResponse(description = "Not Found", responseCode = "404", content = [Content()]),
      ApiResponse(description = "Internal Error", responseCode = "500", content = [Content()])
    ]
  )
  fun findAll(): List<PersonVO> {
    return service.findAll()
  }

  @GetMapping(value = ["/{id}"], produces = [JSON, XML])
  @Operation(
    summary = "Find person by ID", 
    description = "Find person by ID", 
    tags = ["People"],
    responses = [
      ApiResponse(
        description = "Success", 
        responseCode = "200", 
        content = [
          Content(
            array = ArraySchema(schema = Schema(implementation = PersonVO::class))
        )]
      ),
      ApiResponse(description = "Bad Request", responseCode = "400", content = [Content()]),
      ApiResponse(description = "Unauthorized", responseCode = "401", content = [Content()]),
      ApiResponse(description = "Not Found", responseCode = "404", content = [Content()]),
      ApiResponse(description = "Internal Error", responseCode = "500", content = [Content()])
    ]
  )
  fun findById(@PathVariable(value = "id") id: Long): PersonVO {
    return service.findById(id)
  }

  @PostMapping(consumes = [JSON, XML], produces = [JSON, XML])
  @Operation(
    summary = "Create person", 
    description = "Create person", 
    tags = ["People"],
    responses = [
      ApiResponse(
        description = "Success", 
        responseCode = "200", 
        content = [
          Content(
            array = ArraySchema(schema = Schema(implementation = PersonVO::class))
        )]
      ),
      ApiResponse(description = "Bad Request", responseCode = "400", content = [Content()]),
      ApiResponse(description = "Unauthorized", responseCode = "401", content = [Content()]),
      ApiResponse(description = "Not Found", responseCode = "404", content = [Content()]),
      ApiResponse(description = "Internal Error", responseCode = "500", content = [Content()])
    ]
  )
  fun create(@RequestBody person: PersonVO): PersonVO {
    return service.create(person)
  }

  @PutMapping(value = ["/{id}"], consumes = [JSON, XML], produces = [JSON, XML])
  @Operation(
    summary = "Update person", 
    description = "Update person", 
    tags = ["People"],
    responses = [
      ApiResponse(
        description = "Success", 
        responseCode = "200", 
        content = [
          Content(
            array = ArraySchema(schema = Schema(implementation = PersonVO::class))
        )]
      ),
      ApiResponse(description = "Bad Request", responseCode = "400", content = [Content()]),
      ApiResponse(description = "Unauthorized", responseCode = "401", content = [Content()]),
      ApiResponse(description = "Not Found", responseCode = "404", content = [Content()]),
      ApiResponse(description = "Internal Error", responseCode = "500", content = [Content()])
    ]
  )
  fun update(@PathVariable(value = "id") id: Long, @RequestBody person: PersonVO): PersonVO {
    return service.update(id, person)
  }

  @DeleteMapping("/{id}")
  @Operation(
    summary = "Delete person", 
    description = "Delete person", 
    tags = ["People"],
    responses = [
      ApiResponse(
        description = "No Content", 
        responseCode = "201", 
        content = [
          Content(
            array = ArraySchema(schema = Schema(implementation = PersonVO::class))
        )]
      ),
      ApiResponse(description = "Bad Request", responseCode = "400", content = [Content()]),
      ApiResponse(description = "Unauthorized", responseCode = "401", content = [Content()]),
      ApiResponse(description = "Not Found", responseCode = "404", content = [Content()]),
      ApiResponse(description = "Internal Error", responseCode = "500", content = [Content()])
    ]
  )
  fun delete(@PathVariable(value = "id") id: Long): ResponseEntity<Void> {
    service.delete(id)
    return ResponseEntity.noContent().build()
  }
}