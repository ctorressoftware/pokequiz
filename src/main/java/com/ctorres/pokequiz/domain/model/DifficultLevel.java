package com.ctorres.pokequiz.domain.model;

import java.util.Objects;
import java.util.UUID;

public record DifficultLevel(
        UUID id,
        String description,
        boolean active
) {
    public DifficultLevel {
        Objects.requireNonNull(id);
        Objects.requireNonNull(description);
    }
}
