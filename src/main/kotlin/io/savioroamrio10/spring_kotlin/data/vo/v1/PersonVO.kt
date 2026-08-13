package io.savioroamrio10.spring_kotlin.data.vo.v1

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonIgnore

@JsonPropertyOrder("id", "first_name", "last_name", "address", "gender")
class PersonVO(

  var id: Long = 0,

  @field:JsonProperty("first_name")
  var firstName: String = "",

  @field:JsonProperty("last_name")
  var lastName: String = "",
  var address: String = "",

  @JsonIgnore
  var gender: String = ""
)