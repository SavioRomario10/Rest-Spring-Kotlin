package io.savioroamrio10.spring_kotlin.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.servlet.config.annotation.CorsRegistry

@Configuration
class WebConfig: WebMvcConfigurer {

  @Value("\${cors.originPatterns}")
  private val corsOrigin: String = ""

  override fun configureContentNegotiation(configurer: ContentNegotiationConfigurer) {
    configurer
      .favorParameter(true)
      .ignoreAcceptHeader(true)
      .useRegisteredExtensionsOnly(false)
      .parameterName("mediaType")
      .defaultContentType(MediaType.APPLICATION_JSON)
        .mediaType("json", MediaType.APPLICATION_JSON)
        .mediaType("xml", MediaType.APPLICATION_XML)
  }

  override fun addCorsMappings(registry: CorsRegistry){

    val allowedOrigins = corsOrigin.split(",").toTypedArray()

    registry.addMapping("/**")
      .allowedMethods("*")
      .allowedOrigins(*allowedOrigins)
      .allowCredentials(true)
  }
}