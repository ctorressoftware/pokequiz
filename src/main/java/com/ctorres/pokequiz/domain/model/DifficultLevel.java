package com.ctorres.pokequiz.domain.model;

import java.util.Objects;

public record DifficultLevel(
        Long id,
        String description,
        boolean active
) {
    public DifficultLevel {
        Objects.requireNonNull(id);
        Objects.requireNonNull(description);
    }
}
