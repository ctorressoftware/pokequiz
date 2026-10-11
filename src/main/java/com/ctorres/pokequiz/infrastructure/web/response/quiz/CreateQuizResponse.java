package com.ctorres.pokequiz.dto.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateQuizResponse {

    private final Long quizId;

    public CreateQuizResponse(Long quizId) {
        this.quizId = quizId;
    }

    public Long getQuizId() {
        return quizId;
    }
}
