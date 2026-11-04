package com.ctorres.pokequiz.infrastructure.web.request.quiz;

import java.util.UUID;

public class GenerateQuizContentRequest {
    private final UUID quizId;
    private final int questionsQuantity;

    public GenerateQuizContentRequest(UUID quizId, int questionsQuantity) {
        this.quizId = quizId;
        this.questionsQuantity = questionsQuantity;
    }

    public UUID getQuizId() {
        return quizId;
    }

    public int getQuestionsQuantity() {
        return questionsQuantity;
    }
}
