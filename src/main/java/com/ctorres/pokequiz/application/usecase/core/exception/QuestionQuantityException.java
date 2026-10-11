package com.ctorres.pokequiz.application.usecase.core.exception;

public class QuestionQuantityException extends RuntimeException {
    public QuestionQuantityException(String message) {
        super(message);
    }
}
