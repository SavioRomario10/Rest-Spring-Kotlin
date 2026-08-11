package io.savioroamrio10.spring_kotlin.model

import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Column

@Entity
@Table(name = "person")
class Person(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = 0,

  @Column(name = "first_name", nullable = false, length = 80)
  var firstName: String = "",

  @Column(name = "last_name", nullable = false, length = 80)
  var lastName: String = "",

  @Column(name = "address", nullable = false, length = 100)
  var address: String = "",

  @Column(name = "gender", nullable = false, length = 20)
  var gender: String = ""
)