package com.ctorres.pokequiz.dto.api;

public final class UserAnswerDto {
    private final Long questionId;
    private final String description;
    private final String value;

    public UserAnswerDto(
            Long questionId,
            String description,
            String value) {
        this.questionId = questionId;
        this.description = description;
        this.value = value;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public String getDescription() {
        return description;
    }

    public String getValue() {
        return value;
    }
}
