package com.ctorres.pokequiz.dto.api.response;

import com.ctorres.pokequiz.dto.api.generator.GeneratedItem;

import java.util.List;

public class GenerateQuizContentResponse {
    private final Long quizId;
    private final int questionsQuantity;
    private final List<GeneratedItem> questions;

    public GenerateQuizContentResponse(Long quizId, int questionsQuantity, List<GeneratedItem> questions) {
        this.quizId = quizId;
        this.questionsQuantity = questionsQuantity;
        this.questions = questions;
    }

    public Long getQuizId() {
        return  quizId;
    }

    public int getQuestionsQuantity() {
        return questionsQuantity;
    }

    public List<GeneratedItem> getQuestions() {
        return questions;
    }
}
