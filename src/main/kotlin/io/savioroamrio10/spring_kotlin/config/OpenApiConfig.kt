package io.savioroamrio10.spring_kotlin.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Bean

@Configuration
class OpenApiConfig{

  @Bean
  fun custemOpenApi():OpenAPI{
    return OpenAPI()
      .info(
        Info()
          .title("RESTful API with Kotlin and Spring Boot")
          .version("v1")
          .description("RESTful API with Kotlin and Spring Boot")
          .termsOfService("http://swagger.io/terms/")
          .license(
            License()
              .name("Apache 2.0")
              .url("http://springdoc.org"))
      )
  }
}