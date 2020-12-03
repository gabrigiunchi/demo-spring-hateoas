package com.gabrigiunchi.demohateoas.model.hal.templates

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.gabrigiunchi.demohateoas.model.hal.HALLink
import com.gabrigiunchi.demohateoas.model.hal.toPair
import org.springframework.hateoas.Link

@JsonInclude(JsonInclude.Include.NON_NULL)
data class HALTemplate(
    private val title: String,
    val method: String,
    @JsonProperty("content-type") val contentType: String,
    val properties: Collection<HALTemplateProperty> = emptyList(),
    val _links: Map<String, HALLink>? = null
) {
    fun toPair(): Pair<String, HALTemplate> {
        return this.title to this
    }

    fun addLink(link: Link): HALTemplate =
        HALTemplate(
            title = this.title,
            method = this.method,
            contentType = this.contentType,
            properties = this.properties,
            _links = (this._links ?: emptyMap()) + link.toPair()
        )
}