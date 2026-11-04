package com.ctorres.pokequiz.domain.model;

import java.util.Objects;
import java.util.UUID;

public record QuizState(
        UUID id,
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
