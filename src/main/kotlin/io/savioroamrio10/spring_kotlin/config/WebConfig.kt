package io.savioroamrio10.spring_kotlin.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig: WebMvcConfigurer {

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
}