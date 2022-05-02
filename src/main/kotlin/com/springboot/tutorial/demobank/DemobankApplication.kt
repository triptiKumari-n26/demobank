package com.springboot.tutorial.demobank

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DemobankApplication

fun main(args: Array<String>) {
	runApplication<DemobankApplication>(*args)
}
