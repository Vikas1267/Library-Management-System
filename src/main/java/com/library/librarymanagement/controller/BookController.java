package com.library.librarymanagement.controller;

import com.library.librarymanagement.controller.dto.*;
import com.library.librarymanagement.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // CREATE BOOK (ADMIN)
    @PostMapping
    public String addBook(@RequestBody BookRequest request) {
        bookService.addBook(request);
        return "Book saved successfully";
    }

    // GET BOOKS WITH PAGINATION
    @GetMapping
    public PagedResponse<BookResponse> getBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<BookResponse> result =
                bookService.getBooks(PageRequest.of(page, size));

        return new PagedResponse<>(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isLast()
        );
    }

    // SEARCH BOOKS
    @GetMapping("/search")
    public PagedResponse<BookResponse> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<BookResponse> result =
                bookService.searchBooks(title, author, PageRequest.of(page, size));

        return new PagedResponse<>(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isLast()
        );
    }
}
