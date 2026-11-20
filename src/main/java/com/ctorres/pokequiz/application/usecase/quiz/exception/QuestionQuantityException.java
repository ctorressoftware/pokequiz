package com.ctorres.pokequiz.application.usecase.quiz.exception;

public class QuestionQuantityException extends RuntimeException {
    public QuestionQuantityException(String message) {
        super(message);
    }
}
