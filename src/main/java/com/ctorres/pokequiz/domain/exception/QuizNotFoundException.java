package com.ctorres.pokequiz.domain.exception;

import java.util.UUID;

public class QuizNotFoundException extends RuntimeException {

    public QuizNotFoundException(UUID quizId) {
        super("Quiz with Id = " + quizId + " not found.");
    }
}
