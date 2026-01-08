package com.ctorres.pokequiz.controller;

import com.ctorres.pokequiz.dto.api.ApiResponse;
import com.ctorres.pokequiz.dto.api.request.CreateQuizRequest;
import com.ctorres.pokequiz.dto.api.request.GenerateQuizContentRequest;
import com.ctorres.pokequiz.dto.api.response.CreateQuizResponse;
import com.ctorres.pokequiz.dto.api.response.GenerateQuizContentResponse;
import com.ctorres.pokequiz.dto.api.response.QuizDtoResponse;
import com.ctorres.pokequiz.service.QuizService;
import com.ctorres.pokequiz.service.security.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<CreateQuizResponse>> createQuiz(
            @RequestBody CreateQuizRequest request,
            @AuthenticationPrincipal AuthUser user) {
        var response = quizService.createQuiz(request, user);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/generateAndSaveContent")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<QuizDtoResponse>> generateAndSaveContent(
            @RequestBody GenerateQuizContentRequest request,
            @AuthenticationPrincipal AuthUser user) {
        var response = quizService.generateAndSaveContent(request, user);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/getQuizById")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<QuizDtoResponse>> getQuizById(
            @RequestParam Long quizId,
            @AuthenticationPrincipal AuthUser user) {
        var response = quizService.getQuizById(quizId, user);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
