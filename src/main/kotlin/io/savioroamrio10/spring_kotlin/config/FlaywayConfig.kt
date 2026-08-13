package io.savioroamrio10.spring_kotlin.config

import org.flywaydb.core.Flyway
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FlywayConfig {

    @Bean
    fun migrationRunner(flyway: Flyway) = CommandLineRunner {
        println("====== EXECUTANDO FLYWAY ======")
        flyway.migrate()
    }
}
