package com.gabrigiunchi.demohateoas.model

object Constants {

    val BOOKS = (1..10).map { Book(it.toString(), "book$it") }
    val USERS = (1..10).map { User(it.toString(), "name$it", "surname$it") }
}