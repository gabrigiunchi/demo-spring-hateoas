package com.gabrigiunchi.demohateoas.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.util.ResourceUtils

object ResourceLoader {

    inline fun <reified T> loadJSON(path: String): T {
        val resource = ResourceUtils.getFile("classpath:$path")
        return ObjectMapper().registerModule(KotlinModule()).readValue<T>(resource, T::class.java)
    }
}