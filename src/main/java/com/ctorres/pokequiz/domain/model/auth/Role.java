package com.ctorres.pokequiz.domain.model.auth;

import java.util.Objects;
import java.util.UUID;

public record Role(
        UUID id,
        String name,
        String description,
        boolean active
) {

    public Role {
        Objects.requireNonNull(id);
        Objects.requireNonNull(name);
        Objects.requireNonNull(description);
    }
}
