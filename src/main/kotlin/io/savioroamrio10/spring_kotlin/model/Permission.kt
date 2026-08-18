package io.savioroamrio10.spring_kotlin.model

import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.springframework.security.core.GrantedAuthority
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Column

@Entity
@Table(name = "permission")
class Permission : GrantedAuthority{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = 0,

  @Column(name = "description", length = 255)
  var description: String? = null

  override fun getAuthority() =  description!!
  
}