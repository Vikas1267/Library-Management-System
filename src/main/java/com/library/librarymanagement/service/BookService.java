package com.library.librarymanagement.service;

import com.library.librarymanagement.controller.dto.BookRequest;
import com.library.librarymanagement.controller.dto.BookResponse;
import com.library.librarymanagement.entity.Book;
import com.library.librarymanagement.exception.BookNotFoundException;
import com.library.librarymanagement.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void addBook(BookRequest request) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setAvailable(true);
        bookRepository.save(book);
    }

    public void updateBook(Long id, BookRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id " + id));
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id " + id));
        bookRepository.delete(book);
    }

    public Page<BookResponse> getBooks(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(this::toResponse);
    }

    public Page<BookResponse> searchBooks(String title, String author, Pageable pageable) {
        if (title != null) {
            return bookRepository
                    .findByTitleContainingIgnoreCase(title, pageable)
                    .map(this::toResponse);
        }
        if (author != null) {
            return bookRepository
                    .findByAuthorContainingIgnoreCase(author, pageable)
                    .map(this::toResponse);
        }
        return bookRepository.findAll(pageable)
                .map(this::toResponse);
    }

    private BookResponse toResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.isAvailable()
        );
    }
}
