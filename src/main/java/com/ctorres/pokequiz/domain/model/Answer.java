package com.ctorres.pokequiz.domain.model;

import java.util.Objects;

public record Answer(
        Long id,
        String description,
        String canonicalKey,
        boolean correct,
        boolean active,
        Question question
) {
    public Answer {
        Objects.requireNonNull(id);
        Objects.requireNonNull(description);
        Objects.requireNonNull(canonicalKey);
        Objects.requireNonNull(question);
    }
}
