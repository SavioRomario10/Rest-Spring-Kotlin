package io.savioroamrio10.spring_kotlin.data.vo.v1

import java.util.*

data class TokenVO(
  val username: String? = null,
  val authentication: Boolean? = null,
  val created: Date? = null,
  val expiration: Date? = null,
  val accessToken: String? = null,
  val refreshToken: String? = null
)