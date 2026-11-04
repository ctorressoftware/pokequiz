package com.ctorres.pokequiz.application.usecase.core.quiz.evaluation;

import java.util.UUID;

public class EvaluatableQuestion {
    private final UUID questionId;
    private final String correctAnswer;
    private final String userAnswer;

    public EvaluatableQuestion(UUID questionId, String correctAnswer, String userAnswer) {
        this.questionId = questionId;
        this.correctAnswer = correctAnswer;
        this.userAnswer = userAnswer;
    }

    public UUID getQuestionId() {
        return questionId;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getUserAnswer() {
        return userAnswer;
    }
}
