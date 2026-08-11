package io.savioroamrio10.spring_kotlin.exception

import java.util.Date

class ExceptionResponse(
  val timestamp: Date,
  val message: String,
  val details: String
)