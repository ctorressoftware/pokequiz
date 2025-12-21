package com.ctorres.pokequiz.dto.api.request;

public class GenerateQuizContentRequest {
    private final Long quizId;
    private final int questionsQuantity;

    public GenerateQuizContentRequest(Long quizId, int questionsQuantity) {
        this.quizId = quizId;
        this.questionsQuantity = questionsQuantity;
    }

    public Long getQuizId() {
        return quizId;
    }

    public int getQuestionsQuantity() {
        return  questionsQuantity;
    }
}
