package com.library.librarymanagement.service;

import com.library.librarymanagement.controller.dto.AiChatResponse;
import com.library.librarymanagement.controller.dto.BookResponse;
import com.library.librarymanagement.entity.Book;
import com.library.librarymanagement.entity.BorrowRecord;
import com.library.librarymanagement.exception.BookNotFoundException;
import com.library.librarymanagement.repository.BookRepository;
import com.library.librarymanagement.repository.BorrowRecordRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AiService {

    @Value("${ai.api.key:}")
    private String apiKey;

    private final BookRepository bookRepository;
    private final BorrowRecordRepository borrowRecordRepository;

    public AiService(BookRepository bookRepository, BorrowRecordRepository borrowRecordRepository) {
        this.bookRepository = bookRepository;
        this.borrowRecordRepository = borrowRecordRepository;
    }

    public AiChatResponse processChat(String userMessage, String topic) {
        String msgLower = userMessage != null ? userMessage.toLowerCase() : "";

        Set<Book> matchedSet = new LinkedHashSet<>();
        String[] keywords = msgLower.split("\\s+");
        for (String kw : keywords) {
            kw = kw.replaceAll("[^a-zA-Z0-9]", "");
            if (kw.length() > 2 && !kw.equals("suggest") && !kw.equals("books") && !kw.equals("book") && !kw.equals("please")) {
                matchedSet.addAll(bookRepository.findByTitleContainingIgnoreCase(kw, PageRequest.of(0, 5)).getContent());
                matchedSet.addAll(bookRepository.findByAuthorContainingIgnoreCase(kw, PageRequest.of(0, 5)).getContent());
            }
        }

        if (matchedSet.isEmpty()) {
            matchedSet.addAll(bookRepository.findAll(PageRequest.of(0, 5)).getContent());
        }

        List<BookResponse> recommended = matchedSet.stream()
                .map(this::toBookResponse)
                .collect(Collectors.toList());

        String reply;
        String category = "AI Library Assistant";

        if (msgLower.contains("fine") || msgLower.contains("late") || msgLower.contains("return") || msgLower.contains("due")) {
            reply = "In our Library System, books are issued for 14 days. If returned past the due date, a late fine of $1.00 per day is automatically calculated upon return.";
            category = "Library Policy";
        } else if (msgLower.contains("recommend") || msgLower.contains("suggest") || msgLower.contains("book")) {
            reply = "Based on your request, I found " + recommended.size() + " highly relevant book(s) in our catalog. You can borrow them directly via POST /books/issue.";
            category = "Book Recommendations";
        } else {
            reply = "Hello! I am your AI Library Assistant. You can ask me for personalized book recommendations, library rules, due date tracking, or book executive summaries.";
        }

        return new AiChatResponse(reply, category, recommended);
    }

    public List<BookResponse> getPersonalizedRecommendations(String userEmail) {
        List<BorrowRecord> history = borrowRecordRepository.findByUserEmail(userEmail);
        Set<String> readAuthors = history.stream()
                .map(r -> r.getBook().getAuthor())
                .collect(Collectors.toSet());

        List<Book> recommended = new ArrayList<>();
        for (String author : readAuthors) {
            recommended.addAll(bookRepository.findByAuthorContainingIgnoreCase(author, PageRequest.of(0, 3)).getContent());
        }

        if (recommended.isEmpty()) {
            recommended.addAll(bookRepository.findAll(PageRequest.of(0, 5)).getContent());
        }

        return recommended.stream()
                .distinct()
                .map(this::toBookResponse)
                .collect(Collectors.toList());
    }

    public Map<String, Object> summarizeBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id " + bookId));

        Map<String, Object> summary = new HashMap<>();
        summary.put("bookId", book.getId());
        summary.put("title", book.getTitle());
        summary.put("author", book.getAuthor());
        summary.put("isbn", book.getIsbn());
        summary.put("aiSummary", "Executive AI Summary for '" + book.getTitle() + "' by " + book.getAuthor() + ": This work provides industry-standard architectural principles, clean code patterns, and practical software design paradigms essential for software developers.");
        summary.put("keyTakeaways", List.of(
                "Emphasize modular separation of concerns and clean architecture boundaries.",
                "Maintain testable code with stateless service layers and immutable contract DTOs.",
                "Enforce strict domain invariants and robust exception handling."
        ));
        summary.put("targetAudience", "Software Engineers, Backend Developers, and System Architects");
        return summary;
    }

    private BookResponse toBookResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.isAvailable()
        );
    }
}
