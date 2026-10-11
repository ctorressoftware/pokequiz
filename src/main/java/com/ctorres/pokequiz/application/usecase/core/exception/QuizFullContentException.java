package com.ctorres.pokequiz.application.usecase.core.exception;

public class QuizFullContentException extends RuntimeException {
    public QuizFullContentException(Long quizId) {
        super("Quiz with ID = " + quizId + " alredy has questions");
    }
}
