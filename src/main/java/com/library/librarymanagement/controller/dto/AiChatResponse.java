package com.library.librarymanagement.controller.dto;

import java.util.List;

public class AiChatResponse {

    private String reply;
    private String category;
    private List<BookResponse> recommendedBooks;

    public AiChatResponse() {}

    public AiChatResponse(String reply, String category, List<BookResponse> recommendedBooks) {
        this.reply = reply;
        this.category = category;
        this.recommendedBooks = recommendedBooks;
    }

    public String getReply() {
        return reply;
    }

    public String getCategory() {
        return category;
    }

    public List<BookResponse> getRecommendedBooks() {
        return recommendedBooks;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setRecommendedBooks(List<BookResponse> recommendedBooks) {
        this.recommendedBooks = recommendedBooks;
    }
}
