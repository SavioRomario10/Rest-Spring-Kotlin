package io.savioroamrio10.spring_kotlin.controller

import io.swagger.v3.oas.annotations.tags.Tag
import io.savioroamrio10.spring_kotlin.services.AuthService
import io.savioroamrio10.spring_kotlin.data.vo.v1.AccountCredentialsVO

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import io.swagger.v3.oas.annotations.Operation

@Tag(
  name = "Authentication",
  description = "Endpoints for user authentication"
)
@RestController
@RequestMapping("/auth")
class AuthController {

  @Autowired
  lateinit var authService: AuthService

  @PostMapping(value = ["/signin"])
  @Operation(summary = "Signin", description = "Signin", tags = ["Authentication"])
  fun signin( @RequestBody data: AccountCredentialsVO? ): ResponseEntity<*> {

    return if (
      data == null ||
      data.username.isNullOrBlank() ||
      data.password.isNullOrBlank()
    ) {

      ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body("Invalid username/password supplied")

    } else {

      authService.signin(data)
    }
  }
}