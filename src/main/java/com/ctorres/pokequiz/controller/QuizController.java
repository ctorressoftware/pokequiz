package com.ctorres.pokequiz.controller;

import com.ctorres.pokequiz.dto.api.ApiResponse;
import com.ctorres.pokequiz.dto.api.request.CreateQuizRequest;
import com.ctorres.pokequiz.dto.api.request.EvaluateAnswersRequest;
import com.ctorres.pokequiz.dto.api.request.GenerateQuizContentRequest;
import com.ctorres.pokequiz.dto.api.response.CreateQuizResponse;
import com.ctorres.pokequiz.dto.api.response.QuizDtoResponse;
import com.ctorres.pokequiz.service.quiz.QuizOrchestrator;
import com.ctorres.pokequiz.service.security.AuthUser;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    private final QuizOrchestrator quizOrchestrator;

    public QuizController(QuizOrchestrator quizOrchestrator) {
        this.quizOrchestrator = quizOrchestrator;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<CreateQuizResponse>> createQuiz(
            @RequestBody CreateQuizRequest request,
            @AuthenticationPrincipal AuthUser user) {
        var response = quizOrchestrator.createQuiz(request, user);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/generateAndSaveContent")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<QuizDtoResponse>> generateAndSaveContent(
            @RequestBody GenerateQuizContentRequest request,
            @AuthenticationPrincipal AuthUser user) {
        var response = quizOrchestrator.generateAndSaveContent(request, user);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/getQuizById")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<QuizDtoResponse>> getQuizById(
            @RequestParam Long quizId,
            @AuthenticationPrincipal AuthUser user) {
        var response = quizOrchestrator.getQuizById(quizId, user);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/completeQuizAnswers")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<QuizDtoResponse>> completeQuizAnswers(
            @Valid @RequestBody EvaluateAnswersRequest request,
            @AuthenticationPrincipal AuthUser user) {
        var response = quizOrchestrator.completeQuizAnswers(request, user);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
