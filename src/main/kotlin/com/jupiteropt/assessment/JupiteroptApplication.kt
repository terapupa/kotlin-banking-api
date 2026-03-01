package com.jupiteropt.assessment

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(basePackages = ["com.jupiteropt.assessment.repository"])
class JupiteroptApplication

fun main(args: Array<String>) {
    SpringApplication.run(JupiteroptApplication::class.java, *args)
}

