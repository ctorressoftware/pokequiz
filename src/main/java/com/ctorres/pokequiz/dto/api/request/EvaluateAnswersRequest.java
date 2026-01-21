package com.ctorres.pokequiz.dto.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collection;

public final class EvaluateAnswersRequest {
    @JsonProperty(required = true)
    private final Long quizId;

    @JsonProperty(value = "answers", required = true)
    private final Collection<UserAnswerDto> userAnswersDto;

    public EvaluateAnswersRequest(Long quizId, Collection<UserAnswerDto> userAnswersDto) {
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
