package com.gabrigiunchi.demohateoas

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.hateoas.config.EnableHypermediaSupport

@SpringBootApplication
@EnableHypermediaSupport(type = [EnableHypermediaSupport.HypermediaType.HAL_FORMS])
class DemoHateoasApplication

fun main(args: Array<String>) {
    runApplication<DemoHateoasApplication>(*args)
}
