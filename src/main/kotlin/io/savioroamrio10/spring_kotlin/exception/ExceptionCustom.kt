package io.savioroamrio10.spring_kotlin.exception

import java.lang.*
import java.util.Date
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.ResponseEntity
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
@RestController
class ExceptionCustom : ResponseEntityExceptionHandler() {

  @ExceptionHandler(Exception::class)
  fun handleAllExceptions(ex: Exception, request: WebRequest): ResponseEntity<ExceptionResponse> {
    
    val exceptionResponse = ExceptionResponse(
      Date(),
      ex.message ?: "An error occurred",
      request.getDescription(false)
    )

    return ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR)
  }
  @ExceptionHandler(UnsuportedException::class)
  fun handleBadRequest(ex: UnsuportedException, request: WebRequest): ResponseEntity<ExceptionResponse> {
    
    val exceptionResponse = ExceptionResponse(
      Date(),
      ex.message ?: "An error occurred",
      request.getDescription(false)
    )

    return ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR)
  }
}