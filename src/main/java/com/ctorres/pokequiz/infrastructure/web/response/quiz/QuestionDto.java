package com.ctorres.pokequiz.infrastructure.web.response.quiz;

import java.util.Collection;
import java.util.UUID;

public record QuestionDto(
        UUID id,
        String description,
        String frontImageUrl,
        String backImageUrl,
        Collection<AnswerDto> answers,
        Collection<UserAnswerDto> userAnswers
) {}

