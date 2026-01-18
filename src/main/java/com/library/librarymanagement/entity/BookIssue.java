package com.library.librarymanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "book_issues")
public class BookIssue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private LocalDateTime issuedAt;

    private LocalDateTime returnedAt;

    public BookIssue() {}

    public BookIssue(Book book, String userEmail) {
        this.book = book;
        this.userEmail = userEmail;
        this.issuedAt = LocalDateTime.now();
    }

    // getters & setters
    public Long getId() { return id; }

    public Book getBook() { return book; }

    public String getUserEmail() { return userEmail; }

    public LocalDateTime getIssuedAt() { return issuedAt; }

    public LocalDateTime getReturnedAt() { return returnedAt; }

    public void setReturnedAt(LocalDateTime returnedAt) {
        this.returnedAt = returnedAt;
    }
}
