package io.savioroamrio10.spring_kotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StartUp

fun main(args: Array<String>) {
	runApplication<StartUp>(*args)
}