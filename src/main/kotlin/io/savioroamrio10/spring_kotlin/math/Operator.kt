package io.savioroamrio10.spring_kotlin.math

import kotlin.math.sqrt

object Operator {

  fun sum(a: Double, b: Double): Double = a + b
  fun sub(a: Double, b: Double): Double = a - b
  fun mult(a: Double, b: Double): Double = a * b
  fun div(a: Double, b: Double): Double = a / b
  fun mean(a: Double, b: Double): Double = (a + b) / 2
  fun raiz(a: Double): Double = sqrt(a)
}