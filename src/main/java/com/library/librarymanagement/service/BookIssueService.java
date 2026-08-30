package com.library.librarymanagement.service;

import com.library.librarymanagement.controller.dto.BorrowRecordResponse;
import com.library.librarymanagement.entity.Book;
import com.library.librarymanagement.entity.BorrowRecord;
import com.library.librarymanagement.exception.BookAlreadyIssuedException;
import com.library.librarymanagement.exception.BookNotFoundException;
import com.library.librarymanagement.exception.BookNotIssuedException;
import com.library.librarymanagement.repository.BookRepository;
import com.library.librarymanagement.repository.BorrowRecordRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookIssueService {

    private static final int DEFAULT_BORROW_DAYS = 14;
    private static final double DAILY_FINE_RATE = 1.0;

    private final BookRepository bookRepository;
    private final BorrowRecordRepository borrowRecordRepository;

    public BookIssueService(BookRepository bookRepository,
                            BorrowRecordRepository borrowRecordRepository) {
        this.bookRepository = bookRepository;
        this.borrowRecordRepository = borrowRecordRepository;
    }

    public BorrowRecordResponse issueBook(Long bookId, String userEmail) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id " + bookId));

        if (!book.isAvailable()) {
            throw new BookAlreadyIssuedException("Book is already issued");
        }

        book.setAvailable(false);
        bookRepository.save(book);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dueDate = now.plusDays(DEFAULT_BORROW_DAYS);

        BorrowRecord record = new BorrowRecord();
        record.setBook(book);
        record.setUserEmail(userEmail);
        record.setIssuedAt(now);
        record.setDueDate(dueDate);
        record.setFineAmount(0.0);

        BorrowRecord saved = borrowRecordRepository.save(record);
        return mapToResponse(saved);
    }

    public BorrowRecordResponse returnBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id " + bookId));

        BorrowRecord record = borrowRecordRepository.findByBookAndReturnedAtIsNull(book)
                .orElseThrow(() -> new BookNotIssuedException("Book is not currently issued"));

        LocalDateTime returnTime = LocalDateTime.now();
        record.setReturnedAt(returnTime);

        if (record.getDueDate() != null && returnTime.isAfter(record.getDueDate())) {
            long daysOverdue = Duration.between(record.getDueDate(), returnTime).toDays();
            if (daysOverdue == 0) daysOverdue = 1;
            record.setFineAmount(daysOverdue * DAILY_FINE_RATE);
        } else {
            record.setFineAmount(0.0);
        }

        borrowRecordRepository.save(record);

        book.setAvailable(true);
        bookRepository.save(book);

        return mapToResponse(record);
    }

    public List<BorrowRecordResponse> getMyActiveBorrows(String userEmail) {
        return borrowRecordRepository.findByUserEmailAndReturnedAtIsNull(userEmail)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<BorrowRecordResponse> getMyBorrowHistory(String userEmail) {
        return borrowRecordRepository.findByUserEmail(userEmail)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<BorrowRecordResponse> getAllActiveBorrows() {
        return borrowRecordRepository.findByReturnedAtIsNull()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private BorrowRecordResponse mapToResponse(BorrowRecord record) {
        LocalDateTime now = LocalDateTime.now();
        boolean overdue = record.getReturnedAt() == null
                ? (record.getDueDate() != null && now.isAfter(record.getDueDate()))
                : (record.getDueDate() != null && record.getReturnedAt().isAfter(record.getDueDate()));

        return new BorrowRecordResponse(
                record.getId(),
                record.getBook().getId(),
                record.getBook().getTitle(),
                record.getBook().getAuthor(),
                record.getBook().getIsbn(),
                record.getUserEmail(),
                record.getIssuedAt(),
                record.getDueDate(),
                record.getReturnedAt(),
                record.getFineAmount(),
                overdue
        );
    }
}
