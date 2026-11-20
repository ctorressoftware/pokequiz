package com.ctorres.pokequiz.application.usecase.quiz.exception;

import java.util.UUID;

public class QuizInvalidStateTransitionException extends RuntimeException {

    public QuizInvalidStateTransitionException(String message) {
        super(message);
    }

    public QuizInvalidStateTransitionException(UUID quizId) {
        super("Questions were generated, but cannot update status for Quiz with ID = " + quizId);
    }
}
