package com.ctorres.pokequiz.domain.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public record Question(
        Long id,
        String description,
        boolean active,
        String frontImageUrl,
        String backImageUrl,
        Quiz quiz,
        Set<Answer> answers,
        Set<UserAnswer> userAnswers
) {

    public static Question create(
            Long id,
            String description,
            boolean active,
            String frontImageUrl,
            String backImageUrl,
            Quiz quiz
    ) {
        return new Question(
                Objects.requireNonNull(id),
                Objects.requireNonNull(description),
                active,
                frontImageUrl,
                backImageUrl,
                Objects.requireNonNull(quiz),
                new HashSet<>(),
                new HashSet<>()
        );
    }

    public static Question retrieve(
            Long id,
            String description,
            boolean active,
            String frontImageUrl,
            String backImageUrl,
            Quiz quiz,
            Set<Answer> answers,
            Set<UserAnswer> userAnswers
    ) {
        return new Question(
                id,
                description,
                active,
                frontImageUrl,
                backImageUrl,
                quiz,
                answers,
                userAnswers
        );
    }
}
