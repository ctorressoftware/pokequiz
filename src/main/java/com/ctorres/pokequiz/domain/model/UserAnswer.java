package com.ctorres.pokequiz.domain.model;

import java.util.Objects;

public record UserAnswer(
        Long id,
        String description,
        String canonicalKey,
        boolean correct,
        boolean active,
        Question question
) {
    public UserAnswer {
        Objects.requireNonNull(id);
        Objects.requireNonNull(description);
        Objects.requireNonNull(canonicalKey);
        Objects.requireNonNull(question);
    }
}
