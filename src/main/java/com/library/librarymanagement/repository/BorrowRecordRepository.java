package com.library.librarymanagement.repository;

import com.library.librarymanagement.entity.Book;
import com.library.librarymanagement.entity.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {

    Optional<BorrowRecord> findByBookAndReturnedAtIsNull(Book book);

    List<BorrowRecord> findByUserEmailAndReturnedAtIsNull(String userEmail);

    List<BorrowRecord> findByUserEmail(String userEmail);

    List<BorrowRecord> findByReturnedAtIsNull();
}
