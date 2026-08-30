package com.library.librarymanagement.controller.dto;

import java.time.LocalDateTime;

public class BorrowRecordResponse {

    private Long id;
    private Long bookId;
    private String bookTitle;
    private String bookAuthor;
    private String bookIsbn;
    private String userEmail;
    private LocalDateTime issuedAt;
    private LocalDateTime dueDate;
    private LocalDateTime returnedAt;
    private Double fineAmount;
    private boolean overdue;

    public BorrowRecordResponse() {}

    public BorrowRecordResponse(Long id, Long bookId, String bookTitle, String bookAuthor, String bookIsbn,
                                String userEmail, LocalDateTime issuedAt, LocalDateTime dueDate,
                                LocalDateTime returnedAt, Double fineAmount, boolean overdue) {
        this.id = id;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookIsbn = bookIsbn;
        this.userEmail = userEmail;
        this.issuedAt = issuedAt;
        this.dueDate = dueDate;
        this.returnedAt = returnedAt;
        this.fineAmount = fineAmount;
        this.overdue = overdue;
    }

    public Long getId() { return id; }
    public Long getBookId() { return bookId; }
    public String getBookTitle() { return bookTitle; }
    public String getBookAuthor() { return bookAuthor; }
    public String getBookIsbn() { return bookIsbn; }
    public String getUserEmail() { return userEmail; }
    public LocalDateTime getIssuedAt() { return issuedAt; }
    public LocalDateTime getDueDate() { return dueDate; }
    public LocalDateTime getReturnedAt() { return returnedAt; }
    public Double getFineAmount() { return fineAmount; }
    public boolean isOverdue() { return overdue; }
}
