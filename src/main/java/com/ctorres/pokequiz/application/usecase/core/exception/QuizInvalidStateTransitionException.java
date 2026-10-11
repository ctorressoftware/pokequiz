package com.ctorres.pokequiz.exception;

public class QuizInvalidStateTransitionException extends RuntimeException {

    public QuizInvalidStateTransitionException(String message) {
        super(message);
    }

    public QuizInvalidStateTransitionException(Long quizId) {
        super("Questions were generated, but cannot update status for Quiz with ID = " + quizId);
    }
}
