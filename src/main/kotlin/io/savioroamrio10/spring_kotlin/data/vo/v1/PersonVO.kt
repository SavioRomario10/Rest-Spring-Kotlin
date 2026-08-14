package io.savioroamrio10.spring_kotlin.data.vo.v1

import org.springframework.hateoas.RepresentationModel
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonIgnore

@JsonPropertyOrder("id", "first_name", "last_name", "address", "gender")
open class PersonVO(

  var key: Long = 0,

  @field:JsonProperty("first_name")
  var firstName: String = "",

  @field:JsonProperty("last_name")
  var lastName: String = "",
  var address: String = "",

  @JsonIgnore
  var gender: String = ""
):RepresentationModel<PersonVO>()