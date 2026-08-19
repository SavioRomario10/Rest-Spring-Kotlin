package io.savioroamrio10.spring_kotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
//import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder

@SpringBootApplication
class StartUp

fun main(args: Array<String>) {
	runApplication<StartUp>(*args)

//	val encoder = Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8()

//	val senha = "admin123"

//	println(encoder.encode(senha))
}
