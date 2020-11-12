package com.gabrigiunchi.demohateoas.model.hal

import com.fasterxml.jackson.annotation.JsonInclude
import com.gabrigiunchi.demohateoas.model.hal.templates.HALTemplate

@JsonInclude(JsonInclude.Include.NON_NULL)
open class HALResource(
    val _links: Map<String, HALLink>? = null,
    val _templates: Map<String, HALTemplate>? = null
)
