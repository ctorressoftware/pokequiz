package com.ctorres.pokequiz.application.usecase.core.exception;

import java.util.UUID;

public class QuizInvalidStateGenerationException extends RuntimeException {

    public QuizInvalidStateGenerationException(String message) {
        super(message);
    }

    public QuizInvalidStateGenerationException(UUID quizId) {
        super("Cannot perform this operation for Quiz with ID = " + quizId);
    }
}
