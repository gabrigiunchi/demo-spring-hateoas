package com.gabrigiunchi.demohateoas.model.hal.templates

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class HALTemplateProperty(
    val name: String,
    val required: Boolean? = null,
    val readOnly: Boolean? = null,
    val regex: String? = null,
    val value: String? = null
)

