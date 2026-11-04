package com.ctorres.pokequiz.infrastructure.web.request.quiz;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Collection;
import java.util.UUID;

public final class EvaluateAnswersRequest {
    @NotNull(message = "QuizId cannot be null")
    private final UUID quizId;

    @NotEmpty(message = "Invalid answers field")
    private final Collection<@Valid UserAnswerDtoRequest> userAnswersDto; // TODO: Correct name.

    @JsonCreator
    public EvaluateAnswersRequest(
            @JsonProperty("quizId") UUID quizId,
            @JsonProperty("answers") Collection<UserAnswerDtoRequest> userAnswersDto) {
        this.quizId = quizId;
        this.userAnswersDto = userAnswersDto;
    }

    public UUID getQuizId() {
        return quizId;
    }

    public Collection<UserAnswerDtoRequest> getUserAnswersDtos() {
        return userAnswersDto;
    }
}
