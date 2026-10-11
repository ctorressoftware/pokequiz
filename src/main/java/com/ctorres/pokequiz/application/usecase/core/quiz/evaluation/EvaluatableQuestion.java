package com.ctorres.pokequiz.service.quiz.evaluation;

public class EvaluatableQuestion {
    private final Long questionId;
    private final String correctAnswer;
    private final String userAnswer;

    public EvaluatableQuestion(Long questionId, String correctAnswer, String userAnswer) {
        this.questionId = questionId;
        this.correctAnswer = correctAnswer;
        this.userAnswer = userAnswer;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getUserAnswer() {
        return userAnswer;
    }
}
