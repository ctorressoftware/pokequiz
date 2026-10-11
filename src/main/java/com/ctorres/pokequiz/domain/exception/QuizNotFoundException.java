package com.ctorres.pokequiz.exception;

public class QuizNotFoundException extends RuntimeException {

    public QuizNotFoundException(Long quizId) {
        super("Quiz with Id = " + quizId + " not found.");
    }
}
