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


class ResponseCollection(
    _embedded: Map<String, Collection<HALResource>>?,
    _links: Map<String, HALLink>? = null,
    _templates: Map<String, HALTemplate>? = null
) : HALCollection(_embedded, _links, _templates)

class ResponseResource(
    val id: Int,
    val name: String,
    _links: Map<String, HALLink>? = null,
    _templates: Map<String, HALTemplate>? = null
) : HALResource(_links, _templates)


@RestController
@RequestMapping("/custom")
class CustomHALController {

    @GetMapping("/resource")
    fun getResource(): ResponseEntity<HALResource> {
        val result = ResponseResource(
            id = 1,
            name = "resource1",
            _links = mapOf(
                linkTo<CustomHALController> {
                    methodOn(CustomHALController::class.java).getCollection()
                }.withSelfRel().toPair(),

                linkTo<CustomHALController> {
                    methodOn(CustomHALController::class.java).getCollection()
                }.withRel("next").toPair()

            ),
            _templates = mapOf(
                ResourceLoader.loadJSON<HALTemplate>("$TEMPLATES_FOLDER/resource.json").toPair()
            )
        )
        return ResponseEntity.ok(result)
    }

    @GetMapping("/collection")
    fun getCollection(): ResponseEntity<HALCollection> {
        val result = ResponseCollection(
            _links = mapOf(
                linkTo<CustomHALController> {
                    methodOn(CustomHALController::class.java).getCollection()
                }.withSelfRel().toPair(),

                linkTo<CustomHALController> {
                    methodOn(CustomHALController::class.java).getCollection()
                }.withRel("next").toPair()

            ),
            _templates = mapOf(
                ResourceLoader.loadJSON<HALTemplate>("$TEMPLATES_FOLDER/resource.json").toPair()
            ),
            _embedded = mapOf(
                "users" to (1..5).map {
                    ResponseResource(
                        id = it,
                        name = "resource$it",
                        _links = mapOf(
                            linkTo<CustomHALController> {
                                methodOn(CustomHALController::class.java).getCollection()
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