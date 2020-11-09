package com.gabrigiunchi.demohateoas.model

import org.springframework.hateoas.RepresentationModel

data class User(val id: String, var name: String, var surname: String) : RepresentationModel<User>()