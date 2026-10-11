package com.ctorres.pokequiz.exception;

public class QuizFullContentException extends RuntimeException {
    public QuizFullContentException(Long quizId) {
        super("Quiz with ID = " + quizId + " alredy has questions");
    }
}
