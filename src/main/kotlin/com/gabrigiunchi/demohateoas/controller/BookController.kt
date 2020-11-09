package com.gabrigiunchi.demohateoas.controller

import com.gabrigiunchi.demohateoas.model.Book
import com.gabrigiunchi.demohateoas.service.BookService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/books")
class BookController(private val bookService: BookService) {

    @GetMapping
    fun getBooks(): ResponseEntity<Collection<Book>> {
        return ResponseEntity.ok(this.bookService.books)
    }

    @GetMapping("/{id}")
    fun getBookById(@PathVariable id: String): ResponseEntity<Book> {
        val book = this.bookService.getBookById(id)
        return ResponseEntity.ok(book)
    }
}