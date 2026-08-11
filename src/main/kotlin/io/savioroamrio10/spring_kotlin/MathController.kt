package io.savioroamrio10.spring_kotlin

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import io.savioroamrio10.spring_kotlin.exception.*

@RestController
@RequestMapping("/math")
class MathController{

  @GetMapping(value = ["/sum/{a}/{b}"])
  fun sum(@PathVariable(value = "a") a: String, @PathVariable(value = "b") b: String): Double {

    if(!isNumeric(a) || !isNumeric(b)){
      throw UnsuportedException("Please set a numeric value!")
    }

    return convert(a) + convert(b)
  }

  @GetMapping(value = ["/sub/{a}/{b}"])
  fun sub(@PathVariable(value = "a") a: String, @PathVariable(value = "b") b: String): Double {

    if(!isNumeric(a) || !isNumeric(b)){
      throw UnsuportedException("Please set a numeric value!")
    }

    return convert(a) - convert(b)
  }
 
  @GetMapping(value = ["/mult/{a}/{b}"])
  fun mult(@PathVariable(value = "a") a: String, @PathVariable(value = "b") b: String): Double {

    if(!isNumeric(a) || !isNumeric(b)){
      throw UnsuportedException("Please set a numeric value!")
    }

    return convert(a) * convert(b)
  }

  @GetMapping(value = ["/div/{a}/{b}"])
  fun div(@PathVariable(value = "a") a: String, @PathVariable(value = "b") b: String): Double {

    if(!isNumeric(a) || !isNumeric(b)){
      throw UnsuportedException("Please set a numeric value!")
    }

    if(convert(b) == 0.0){
      throw UnsuportedException("The divisor cannot be zero!")
    }

    return convert(a) / convert(b)
  }

  @GetMapping(value = ["/mean/{a}/{b}"])
  fun mean(@PathVariable(value = "a") a: String, @PathVariable(value = "b") b: String): Double {

    if(!isNumeric(a) || !isNumeric(b)){
      throw UnsuportedException("Please set a numeric value!")
    }

    return (convert(a) + convert(b)) / 2
  }
  
  @GetMapping(value = ["/sqrt/{a}"])
  fun sqrt(@PathVariable(value = "a") a: String): Double {

    if(!isNumeric(a)){
      throw UnsuportedException("Please set a numeric value!")
    }

    return Math.sqrt(convert(a))
  }

  private fun convert(strNumber: String): Double {
    return strNumber.replace(",", ".").toDouble()
  }

  private fun isNumeric(strNumber: String): Boolean {
      return strNumber.replace(",", ".").toDoubleOrNull() != null
  }
}