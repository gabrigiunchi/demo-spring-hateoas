package com.gabrigiunchi.demohateoas.service

import com.gabrigiunchi.demohateoas.model.Book
import com.gabrigiunchi.demohateoas.model.Constants
import org.springframework.stereotype.Service


@Service
class BookService {

    val books: Set<Book>
        get() = Constants.BOOKS.toSet()

    fun getBookById(id: String): Book = this.books.find { it.id == id } ?: throw Exception("book not found")
}