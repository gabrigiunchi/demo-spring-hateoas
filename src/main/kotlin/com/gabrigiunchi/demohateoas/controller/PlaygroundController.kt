package com.gabrigiunchi.demohateoas.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/playground")
class PlaygroundController {

    @GetMapping
    fun get(): ResponseEntity<Any> {
        return ResponseEntity.ok(1)
    }
}