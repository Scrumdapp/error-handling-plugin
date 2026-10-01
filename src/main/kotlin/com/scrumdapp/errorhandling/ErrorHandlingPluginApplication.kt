package com.scrumdapp.errorhandling

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PassportPluginApplication

fun main(args: Array<String>) {
    runApplication<PassportPluginApplication>(*args)
}