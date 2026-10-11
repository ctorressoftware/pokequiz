package com.ctorres.pokequiz.application.usecase.core.exception;

public class QuizInvalidStateGenerationException extends RuntimeException {

    public QuizInvalidStateGenerationException(String message) {
        super(message);
    }

    public QuizInvalidStateGenerationException(Long quizId) {
        super("Cannot perform this operation for Quiz with ID = " + quizId);
    }
}
