package com.library.librarymanagement.repository;

import com.library.librarymanagement.entity.Book;
import com.library.librarymanagement.entity.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {

    // For Phase 16: find active (not yet returned) record for a book
    Optional<BorrowRecord> findByBookAndReturnedAtIsNull(Book book);

    // For history APIs
    List<BorrowRecord> findByUserEmail(String userEmail);
}
