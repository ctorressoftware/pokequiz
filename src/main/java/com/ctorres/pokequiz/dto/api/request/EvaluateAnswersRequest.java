package com.ctorres.pokequiz.dto.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Collection;

public final class EvaluateAnswersRequest {
    @NotNull(message = "QuizId cannot be null")
    @Positive(message = "QuizId must be greater than 0")
    private final Long quizId;

    @NotEmpty(message = "Invalid answers field")
    private final Collection<@Valid UserAnswerDto> userAnswersDto;

    @JsonCreator
    public EvaluateAnswersRequest(
            @JsonProperty("quizId") Long quizId,
            @JsonProperty("answers") Collection<UserAnswerDto> userAnswersDto) {
        this.quizId = quizId;
        this.userAnswersDto = userAnswersDto;
    }

    public Long getQuizId() {
        return quizId;
    }

    public Collection<UserAnswerDto> getUserAnswersDtos() {
        return userAnswersDto;
    }
}
