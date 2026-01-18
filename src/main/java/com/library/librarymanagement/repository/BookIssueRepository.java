package com.library.librarymanagement.repository;

import com.library.librarymanagement.entity.Book;
import com.library.librarymanagement.entity.BookIssue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookIssueRepository extends JpaRepository<BookIssue, Long> {

    Optional<BookIssue> findByBookAndReturnedAtIsNull(Book book);

    List<BookIssue> findByUserEmail(String userEmail);
}
