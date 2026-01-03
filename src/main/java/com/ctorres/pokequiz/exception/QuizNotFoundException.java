package com.ctorres.pokequiz.exception;

public class QuizNotFoundException extends RuntimeException {

    public QuizNotFoundException(Long quizId) {
        super("Cannot find quiz by id: " + quizId);
    }
}
