package com.ctorres.pokequiz.infrastructure.web.response.quiz;

import com.ctorres.pokequiz.domain.model.DifficultLevel;
import com.ctorres.pokequiz.domain.model.Question;
import com.ctorres.pokequiz.domain.model.QuizState;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record QuizDto(
        UUID id,
        Instant startDate,
        Instant endDate,
        QuizState state,
        DifficultLevel difficultLevel,
        Set<Question> questions,
        String username
) {}
