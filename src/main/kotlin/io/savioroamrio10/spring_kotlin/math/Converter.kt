package io.savioroamrio10.spring_kotlin.math

object Converter {

  fun convert(strNumber: String): Double {
    return strNumber.replace(",", ".").toDouble()
  }

  fun isNumeric(strNumber: String): Boolean {
    return strNumber.replace(",", ".").toDoubleOrNull() != null
  }
}