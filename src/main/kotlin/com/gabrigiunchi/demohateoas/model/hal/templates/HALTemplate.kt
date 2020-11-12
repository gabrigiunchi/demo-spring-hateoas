package com.gabrigiunchi.demohateoas.model.hal.templates

import com.fasterxml.jackson.annotation.JsonProperty

data class HALTemplate(
    private val title: String,
    val method: String,
    @JsonProperty("content-type") val contentType: String,
    val properties: Collection<HALTemplateProperty> = emptyList()
) {
    fun toPair(): Pair<String, HALTemplate> {
        return this.title to this
    }
}