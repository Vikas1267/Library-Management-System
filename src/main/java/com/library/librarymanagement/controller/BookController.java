package com.library.librarymanagement.controller;

import com.library.librarymanagement.controller.dto.*;
import com.library.librarymanagement.service.BookIssueService;
import com.library.librarymanagement.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@Tag(name = "Books & Borrowing", description = "Endpoints for managing books, self-service borrowing, returning, and reading records.")
public class BookController {

    private final BookService bookService;
    private final BookIssueService bookIssueService;

    public BookController(BookService bookService, BookIssueService bookIssueService) {
        this.bookService = bookService;
        this.bookIssueService = bookIssueService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    @Operation(summary = "Add a new book (ADMIN / LIBRARIAN)")
    public String addBook(@RequestBody BookRequest request) {
        bookService.addBook(request);
        return "Book saved successfully";
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    @Operation(summary = "Update book details (ADMIN / LIBRARIAN)")
    public String updateBook(@PathVariable Long id, @RequestBody BookRequest request) {
        bookService.updateBook(id, request);
        return "Book updated successfully";
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    @Operation(summary = "Delete a book (ADMIN / LIBRARIAN)")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "Book deleted successfully";
    }

    @GetMapping
    @Operation(summary = "Get paginated list of books (ALL)")
    public PagedResponse<BookResponse> getBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<BookResponse> result = bookService.getBooks(PageRequest.of(page, size));

        return new PagedResponse<>(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isLast()
        );
    }

    @GetMapping("/search")
    @Operation(summary = "Search books by title or author (ALL)")
    public PagedResponse<BookResponse> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<BookResponse> result = bookService.searchBooks(title, author, PageRequest.of(page, size));

        return new PagedResponse<>(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isLast()
        );
    }

    @PostMapping("/issue")
    @Operation(summary = "Borrow / Issue a book (MEMBER / ADMIN)")
    public BorrowRecordResponse issueBook(@RequestBody IssueBookRequest request, Authentication authentication) {
        String targetEmail = (request.getUserEmail() != null && !request.getUserEmail().isBlank())
                ? request.getUserEmail()
                : authentication.getName();

        return bookIssueService.issueBook(request.getBookId(), targetEmail);
    }

    @PostMapping("/return/{bookId}")
    @Operation(summary = "Return a borrowed book (Calculates late fine if overdue)")
    public BorrowRecordResponse returnBook(@PathVariable Long bookId) {
        return bookIssueService.returnBook(bookId);
    }

    @GetMapping("/my-borrowed")
    @Operation(summary = "Get active borrowed books for logged-in member")
    public List<BorrowRecordResponse> getMyActiveBorrows(Authentication authentication) {
        return bookIssueService.getMyActiveBorrows(authentication.getName());
    }

    @GetMapping("/my-history")
    @Operation(summary = "Get full borrowing history & fines for logged-in member")
    public List<BorrowRecordResponse> getMyBorrowHistory(Authentication authentication) {
        return bookIssueService.getMyBorrowHistory(authentication.getName());
    }

    @GetMapping("/admin/all-issued")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    @Operation(summary = "View all currently issued books across the library (ADMIN)")
    public List<BorrowRecordResponse> getAllActiveBorrows() {
        return bookIssueService.getAllActiveBorrows();
    }
}
