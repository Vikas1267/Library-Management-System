package com.library.librarymanagement.controller;

import com.library.librarymanagement.controller.dto.AiChatRequest;
import com.library.librarymanagement.controller.dto.AiChatResponse;
import com.library.librarymanagement.controller.dto.BookResponse;
import com.library.librarymanagement.service.AiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ai")
@Tag(name = "AI Library Assistant", description = "AI-powered chat assistant, personalized book recommendation engine, and AI book summarizer.")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/chat")
    @Operation(summary = "Ask the AI Library Assistant natural language questions or request book recommendations")
    public AiChatResponse chat(@RequestBody AiChatRequest request) {
        return aiService.processChat(request.getMessage(), request.getTopic());
    }

    @GetMapping("/recommendations")
    @Operation(summary = "Get personalized AI book recommendations based on user reading history")
    public List<BookResponse> getRecommendations(Authentication authentication) {
        String email = authentication != null ? authentication.getName() : "admin@library.com";
        return aiService.getPersonalizedRecommendations(email);
    }

    @GetMapping("/summarize/{bookId}")
    @Operation(summary = "Generate an AI executive summary and key takeaways for a book")
    public Map<String, Object> summarizeBook(@PathVariable Long bookId) {
        return aiService.summarizeBook(bookId);
    }
}
