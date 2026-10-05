package com.ctorres.pokequiz.domain.model;

import java.util.Objects;

public record QuizState(
        Long id,
        String description,
        String code,
        boolean active
) {
    public QuizState {
        Objects.requireNonNull(id);
        Objects.requireNonNull(description);
        Objects.requireNonNull(code);
    }
}
