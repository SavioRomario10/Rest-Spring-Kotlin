package io.savioroamrio10.spring_kotlin.data.vo.v2

import java.util.Date

class PersonVO(

  var id: Long = 0,
  var firstName: String = "",
  var lastName: String = "",
  var address: String = "",
  var gender: String = "",
  var birthDay: Date? = null
)