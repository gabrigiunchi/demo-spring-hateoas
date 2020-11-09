package com.gabrigiunchi.demohateoas.model

import org.springframework.hateoas.RepresentationModel

data class Book(val id: String, val name: String) : RepresentationModel<Book>()