package com.ctorres.pokequiz.dto.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public final class UserAnswerDto {
    @NotNull(message = "questionId cannot be null")
    @Positive(message = "questionId must be greater than 0")
    private final Long questionId;

    @NotBlank(message = "description field is invalid")
    @Size(max = 100, message = "description field is too long")
    private final String description;

    @NotBlank(message = "value field is invalid")
    @Size(max = 100, message = "value field is too long")
    private final String value;

    @JsonCreator
    public UserAnswerDto(
            @JsonProperty("questionId") Long questionId,
            @JsonProperty("description") String description,
            @JsonProperty("value") String value) {
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
