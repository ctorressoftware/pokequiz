package com.ctorres.pokequiz.domain.model;

import com.ctorres.pokequiz.domain.model.auth.User;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public record Quiz(
        Long id,
        Instant startDate,
        Instant endDate,
        QuizState state,
        DifficultLevel difficultLevel,
        User user,
        Set<Question> questions
) {
    public static Quiz create(
            Long id,
            QuizState state,
            DifficultLevel difficultLevel,
            User user
    ) {
        return new Quiz(
                Objects.requireNonNull(id),
                null,
                null,
                Objects.requireNonNull(state),
                Objects.requireNonNull(difficultLevel),
                Objects.requireNonNull(user),
                new HashSet<>()
        );
    }

    public static Quiz retrieve(
            Long id,
            Instant startDate,
            Instant endDate,
            QuizState state,
            DifficultLevel difficultLevel,
            User user,
            Set<Question> questions
    ) {
        return new Quiz(
                id,
                startDate,
                endDate,
                state,
                difficultLevel,
                user,
                questions
        );
    }
}