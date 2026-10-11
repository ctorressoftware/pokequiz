package com.ctorres.pokequiz.infrastructure.web.api;

import com.ctorres.pokequiz.application.usecase.auth.exception.DuplicatedUsernameException;
import com.ctorres.pokequiz.application.usecase.auth.exception.InactiveUserException;
import com.ctorres.pokequiz.application.usecase.auth.exception.InvalidRefreshTokenException;
import com.ctorres.pokequiz.application.usecase.auth.exception.WeakPasswordException;
import com.ctorres.pokequiz.application.usecase.core.exception.*;
import com.ctorres.pokequiz.domain.exception.DifficultLevelNotFoundException;
import com.ctorres.pokequiz.domain.exception.QuizNotFoundException;
import com.ctorres.pokequiz.application.usecase.core.generator.GeneratedItem;
import com.ctorres.pokequiz.infrastructure.web.response.quiz.CreateQuizResponse;
import com.ctorres.pokequiz.infrastructure.web.response.quiz.GenerateQuizContentResponse;
import com.ctorres.pokequiz.infrastructure.web.response.quiz.QuizDtoResponse;
import com.ctorres.pokequiz.infrastructure.web.response.auth.TokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

// TODO: Add more exceptions
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatedUsernameException.class)
    public ResponseEntity<ApiResponse<TokenResponse>> handleDuplicatedUsernameException(
            DuplicatedUsernameException e) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(400, e.getMessage()));
    }

    @ExceptionHandler(WeakPasswordException.class)
    public ResponseEntity<ApiResponse<TokenResponse>> handleWeakPasswordException(
            WeakPasswordException e) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(400, e.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<TokenResponse>> handleBadCredentialsException() {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error(HttpStatus.UNAUTHORIZED.value(), "Bad credentials"));
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<ApiResponse<TokenResponse>> handleInvalidRefreshTokenException(
            InvalidRefreshTokenException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error(HttpStatus.UNAUTHORIZED.value(), e.getMessage()));
    }

    @ExceptionHandler(InactiveUserException.class)
    public ResponseEntity<ApiResponse<TokenResponse>> handleInactiveUserException() {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error(HttpStatus.UNAUTHORIZED.value(), "Bad credentials"));
    }

    @ExceptionHandler(QuestionQuantityException.class)
    ResponseEntity<ApiResponse<List<GeneratedItem>>> handleQuestionQuantityException(
            QuestionQuantityException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    @ExceptionHandler(DifficultLevelNotFoundException.class)
    ResponseEntity<ApiResponse<CreateQuizResponse>> handleDifficultLevelNotFoundException(
            DifficultLevelNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }

    @ExceptionHandler(GenerationModuleException.class)
    ResponseEntity<ApiResponse<Object>> handleGenerationModuleException(
            GenerationModuleException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }

    @ExceptionHandler(QuizNotFoundException.class)
    ResponseEntity<ApiResponse<Object>> handleQuizNotFoundException(QuizNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }

    @ExceptionHandler(QuizFullContentException.class)
    ResponseEntity<ApiResponse<GenerateQuizContentResponse>> handleQuizFullContentException(
            QuizFullContentException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(HttpStatus.CONFLICT.value(), e.getMessage()));
    }

    @ExceptionHandler(QuizInvalidStateGenerationException.class)
    ResponseEntity<ApiResponse<QuizDtoResponse>> handleQuizInvalidStateGenerationException(
            QuizInvalidStateGenerationException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(HttpStatus.CONFLICT.value(), e.getMessage()));
    }

    @ExceptionHandler(QuizInvalidStateTransitionException.class)
    ResponseEntity<ApiResponse<QuizDtoResponse>> handleQuizInvalidStateTransitionException(
            QuizInvalidStateTransitionException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
}