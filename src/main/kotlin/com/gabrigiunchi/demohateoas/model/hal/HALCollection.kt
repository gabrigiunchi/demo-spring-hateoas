package com.gabrigiunchi.demohateoas.model.hal

import com.gabrigiunchi.demohateoas.model.hal.templates.HALTemplate

open class HALCollection(
    val _embedded: Map<String, Collection<HALResource>>? = null,
    _links: Map<String, HALLink>? = null,
    _templates: Map<String, HALTemplate>? = null
) : HALResource(_links, _templates)