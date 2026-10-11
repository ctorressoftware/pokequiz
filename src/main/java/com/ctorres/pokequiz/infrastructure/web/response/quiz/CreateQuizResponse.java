package com.ctorres.pokequiz.infrastructure.web.response.quiz;

public class CreateQuizResponse {

    private final Long quizId;

    public CreateQuizResponse(Long quizId) {
        this.quizId = quizId;
    }

    public Long getQuizId() {
        return quizId;
    }
}
