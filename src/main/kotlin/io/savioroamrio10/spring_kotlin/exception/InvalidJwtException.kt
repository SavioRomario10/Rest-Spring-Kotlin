package io.savioroamrio10.spring_kotlin.exception

import java.lang.*
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.security.core.AuthenticationException

@ResponseStatur(HttpStatus.FORBIDDEN)
class InvalidJwtException(execption: String?) : AuthenticationException(execption) {

}