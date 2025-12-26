package com.ctorres.pokequiz.controller;

import com.ctorres.pokequiz.dto.api.ApiResponse;
import com.ctorres.pokequiz.dto.api.request.CreateQuizRequest;
import com.ctorres.pokequiz.dto.api.request.GenerateQuizContentRequest;
import com.ctorres.pokequiz.dto.api.response.CreateQuizResponse;
import com.ctorres.pokequiz.dto.api.response.GenerateQuizContentResponse;
import com.ctorres.pokequiz.service.QuizService;
import com.ctorres.pokequiz.service.security.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<ApiResponse<GenerateQuizContentResponse>> generateAndSaveContent(
            @RequestBody GenerateQuizContentRequest request,
            @AuthenticationPrincipal AuthUser user) {
        var response = quizService.generateAndSaveContent(request, user);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
