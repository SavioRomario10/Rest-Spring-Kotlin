package io.savioroamrio10.spring_kotlin.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {

  @Value("\${cors.originPatterns}")
  private lateinit var corsOrigin: String

  override fun configureContentNegotiation( configurer: ContentNegotiationConfigurer) {

    configurer
      .favorParameter(true)
      .ignoreAcceptHeader(true)
      .useRegisteredExtensionsOnly(false)
      .parameterName("mediaType")
      .defaultContentType(MediaType.APPLICATION_JSON)
      .mediaType("json", MediaType.APPLICATION_JSON)
      .mediaType("xml", MediaType.APPLICATION_XML)
  }

  override fun addCorsMappings( registry: CorsRegistry) {

    val allowedOrigins = corsOrigin
      .split(",")
      .map { it.trim() }
      .toTypedArray()

    registry.addMapping("/**")
      .allowedOriginPatterns(*allowedOrigins)
      .allowedMethods("*")
      .allowedHeaders("*")
      .allowCredentials(true)
  }
}