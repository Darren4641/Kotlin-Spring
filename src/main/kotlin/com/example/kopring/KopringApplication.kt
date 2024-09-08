package com.example.kopring

import com.example.kopring.common.config.AppProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties(AppProperties::class)
class KopringApplication

fun main(args: Array<String>) {
    runApplication<KopringApplication>(*args)
}
