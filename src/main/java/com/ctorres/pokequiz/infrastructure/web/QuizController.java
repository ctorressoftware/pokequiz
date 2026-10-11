package com.ctorres.pokequiz.infrastructure.web;

import com.ctorres.pokequiz.infrastructure.web.api.ApiResponse;
import com.ctorres.pokequiz.infrastructure.web.request.quiz.CreateQuizRequest;
import com.ctorres.pokequiz.infrastructure.web.request.quiz.EvaluateAnswersRequest;
import com.ctorres.pokequiz.infrastructure.web.request.quiz.GenerateQuizContentRequest;
import com.ctorres.pokequiz.infrastructure.web.response.quiz.CreateQuizResponse;
import com.ctorres.pokequiz.infrastructure.web.response.quiz.QuizDtoResponse;
import com.ctorres.pokequiz.application.usecase.core.quiz.QuizOrchestrator;
import com.ctorres.pokequiz.infrastructure.security.AuthUser;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

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
            @RequestHeader(name = "Accept-Language", required = false) Locale locale,
            @AuthenticationPrincipal AuthUser user) {
        var response = quizOrchestrator.generateAndSaveContent(request, locale, user);
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
            @Valid @RequestBody EvaluateAnswersRequest request, // TODO: Configure @Valid in each request
            @AuthenticationPrincipal AuthUser user) {
        var response = quizOrchestrator.completeQuizAnswers(request, user);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
