package com.ctorres.pokequiz.infrastructure.web;

import com.ctorres.pokequiz.application.port.in.quiz.complete.CompleteQuizCommand;
import com.ctorres.pokequiz.application.port.in.quiz.complete.CompleteQuizResult;
import com.ctorres.pokequiz.application.port.in.quiz.create.CreateQuizCommand;
import com.ctorres.pokequiz.application.port.in.quiz.generate.GenerateQuizCommand;
import com.ctorres.pokequiz.application.port.in.quiz.generate.GenerateQuizResult;
import com.ctorres.pokequiz.application.port.in.quiz.getById.GetQuizByIdCommand;
import com.ctorres.pokequiz.application.port.in.quiz.getById.GetQuizByIdResult;
import com.ctorres.pokequiz.application.usecase.core.quiz.useranswer.SubmittedUserAnswer;
import com.ctorres.pokequiz.domain.model.UserAnswer;
import com.ctorres.pokequiz.infrastructure.mapper.UserMapper;
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
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    private final QuizOrchestrator quizOrchestrator;
    private final UserMapper userMapper;

    public QuizController(QuizOrchestrator quizOrchestrator) {
        this.quizOrchestrator = quizOrchestrator;
        this.userMapper = new UserMapper();
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<CreateQuizResponse>> createQuiz(
            @RequestBody CreateQuizRequest request,
            @AuthenticationPrincipal AuthUser user
    ) {

        var result = quizOrchestrator.createQuiz(new CreateQuizCommand(
                request.getDifficultLevelId(),
                userMapper.map(user)
        ));

        return ResponseEntity.ok(ApiResponse.success(
                new CreateQuizResponse(result.quizId())
        ));
    }

    @PostMapping("/generateAndSaveContent")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<GenerateQuizResult>> generateAndSaveContent(
            @RequestBody GenerateQuizContentRequest request,
            @RequestHeader(name = "Accept-Language", required = false) Locale locale,
            @AuthenticationPrincipal AuthUser user) {

        GenerateQuizResult result = quizOrchestrator.generateAndSaveContent(new GenerateQuizCommand(
                request.getQuizId(),
                request.getQuestionsQuantity(),
                locale,
                userMapper.map(user)
        ));

        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/getQuizById")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<GetQuizByIdResult>> getQuizById(
            @RequestParam UUID quizId,
            @AuthenticationPrincipal AuthUser user) {
        var response = quizOrchestrator.getQuizById(new GetQuizByIdCommand(quizId, userMapper.map(user)));
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/completeQuizAnswers")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<CompleteQuizResult>> completeQuizAnswers(
            @Valid @RequestBody EvaluateAnswersRequest request, // TODO: Configure @Valid in each request
            @AuthenticationPrincipal AuthUser user) {

        var response = quizOrchestrator.completeQuizAnswers(new CompleteQuizCommand(
                request.getQuizId(),
                request.getUserAnswersDtos().stream().map(u ->
                        new SubmittedUserAnswer(
                                u.getQuestionId(),
                                u.getDescription(),
                                u.getValue()
                        )
                ).collect(Collectors.toSet()),
                userMapper.map(user)
        ));

        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
