package com.gabrigiunchi.demohateoas.model.hal

import org.springframework.hateoas.Link

fun Link.toPair(): Pair<String, HALLink> {
    return this.rel.value() to HALLink(this.href)
}
