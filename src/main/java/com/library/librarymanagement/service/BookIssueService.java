package com.library.librarymanagement.service;

import com.library.librarymanagement.entity.Book;
import com.library.librarymanagement.entity.BorrowRecord;
import com.library.librarymanagement.exception.BookAlreadyIssuedException;
import com.library.librarymanagement.exception.BookNotFoundException;
import com.library.librarymanagement.exception.BookNotIssuedException;
import com.library.librarymanagement.repository.BookRepository;
import com.library.librarymanagement.repository.BorrowRecordRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BookIssueService {

    private final BookRepository bookRepository;
    private final BorrowRecordRepository borrowRecordRepository;

    public BookIssueService(BookRepository bookRepository,
                            BorrowRecordRepository borrowRecordRepository) {
        this.bookRepository = bookRepository;
        this.borrowRecordRepository = borrowRecordRepository;
    }

    public void issueBook(Long bookId, String userEmail) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id " + bookId));

        if (!book.isAvailable()) {
            throw new BookAlreadyIssuedException("Book is already issued");
        }

        book.setAvailable(false);
        bookRepository.save(book);

        BorrowRecord record = new BorrowRecord();
        record.setBook(book);
        record.setUserEmail(userEmail);
        record.setIssuedAt(LocalDateTime.now());

        borrowRecordRepository.save(record);
    }

    public void returnBook(Long bookId) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id " + bookId));

        BorrowRecord record = borrowRecordRepository.findByBookAndReturnedAtIsNull(book)
                .orElseThrow(() -> new BookNotIssuedException("Book is not currently issued"));

        record.setReturnedAt(LocalDateTime.now());
        borrowRecordRepository.save(record);

        book.setAvailable(true);
        bookRepository.save(book);
    }
}
