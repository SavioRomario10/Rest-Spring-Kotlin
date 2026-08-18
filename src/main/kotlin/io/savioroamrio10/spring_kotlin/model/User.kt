package io.savioroamrio10.spring_kotlin.model

import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

import io.savioroamrio10.spring_kotlin.model.Permission
import java.util.*

@Entity
@Table(name = "users")
class User: UserDetails{
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = 0

  @Column(name = "user_name", unique = true)
  var userName: String? = null

  @Column(name = "full_name")
  var fullName: String? = null

  @Column(name = "password")
  private var password: String? = null

  @Column(name = "account_non_expired")
  var accountNonExpired: Boolean? = null

  @Column(name = "account_non_locked")
  var accountNonLocked: Boolean? = null

  @Column(name = "credentials_non_expired")
  var credentialsNonExpired: Boolean? = null

  @Column(name = "enabled")
  var enabled: Boolean? = null

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "users_permissions", 
    joinColumns = [JoinColumn(name = "user_id")], inverseJoinColumns = [JoinColumn(name = "permission_id")]
  )
  var permissions: List<Permission>? = null

  val role: List<String?>
    get() {
      val role = mutableListOf<String?>()
      for (permission in permissions!!) {
        role.add(permission.description)
      }
      return role
    }

  override fun getAuthorities(): Collection<GrantedAuthority> = permissions!!

  override fun getPassword(): String? = password!!

  override fun getUsername(): String? = userName!!

  override fun isAccountNonExpired(): Boolean = accountNonExpired!!

  override fun isAccountNonLocked(): Boolean = accountNonLocked!!

  override fun isCredentialsNonExpired(): Boolean = credentialsNonExpired!!

  override fun isEnabled(): Boolean = enabled!!

}