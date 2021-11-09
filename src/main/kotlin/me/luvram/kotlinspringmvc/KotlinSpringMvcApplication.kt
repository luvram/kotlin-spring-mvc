package me.luvram.kotlinspringmvc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinSpringMvcApplication

fun main(args: Array<String>) {
    runApplication<KotlinSpringMvcApplication>(*args)
}
