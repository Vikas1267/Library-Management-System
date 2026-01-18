package com.library.librarymanagement.controller.dto;

public class IssueBookRequest {

    private Long bookId;
    private String userEmail;

    public Long getBookId() {
        return bookId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
