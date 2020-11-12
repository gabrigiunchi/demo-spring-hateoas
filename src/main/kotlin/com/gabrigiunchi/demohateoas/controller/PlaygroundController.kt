package com.gabrigiunchi.demohateoas.controller


import com.gabrigiunchi.demohateoas.model.hal.HALCollection
import com.gabrigiunchi.demohateoas.model.hal.HALLink
import com.gabrigiunchi.demohateoas.model.hal.HALResource
import com.gabrigiunchi.demohateoas.model.hal.templates.HALTemplate
import com.gabrigiunchi.demohateoas.model.hal.toPair
import com.gabrigiunchi.demohateoas.service.ResourceLoader
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


class Response(
    _embedded: Map<String, Collection<HALResource>>?,
    _links: Map<String, HALLink>? = null,
    _templates: Map<String, HALTemplate>? = null
) : HALCollection(_embedded, _links, _templates)

class Child(
    val id: Int,
    _links: Map<String, HALLink>? = null
) : HALResource(_links)


@RestController
@RequestMapping("/playground")
class PlaygroundController {

    @GetMapping
    fun get(): ResponseEntity<Any> {
        val result = Response(
            _links = mapOf(
                linkTo<PlaygroundController> {
                    methodOn(PlaygroundController::class.java).get()
                }.withSelfRel().toPair(),

                linkTo<PlaygroundController> {
                    methodOn(PlaygroundController::class.java).get()
                }.withRel("next").toPair()

            ),
            _templates = mapOf(
                ResourceLoader.loadJSON<HALTemplate>("$TEMPLATES_FOLDER/resource.json").toPair()
            ),
            _embedded = mapOf(
                "users" to (1..5).map {
                    Child(
                        id = it,
                        _links = mapOf(
                            linkTo<PlaygroundController> {
                                methodOn(PlaygroundController::class.java).get()
                            }.withSelfRel().toPair()
                        )
                    )
                }
            )
        )
        return ResponseEntity.ok(result)
    }

    companion object {
        const val TEMPLATES_FOLDER = "hal/templates"
    }
}