package com.ctorres.pokequiz.infrastructure.web.response.quiz;

import java.util.UUID;

public final class UserAnswerDto {
    private final UUID questionId;
    private final String description;
    private final String value;

    public UserAnswerDto(
            UUID questionId,
            String description,
            String value) {
        this.questionId = questionId;
        this.description = description;
        this.value = value;
    }

    public UUID getQuestionId() {
        return questionId;
    }

    public String getDescription() {
        return description;
    }

    public String getValue() {
        return value;
    }
}
