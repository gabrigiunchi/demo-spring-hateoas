package com.gabrigiunchi.demohateoas.model

import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation

@Relation(value = "user", collectionRelation = "users")
data class User(val id: String, var name: String, var surname: String) : RepresentationModel<User>()