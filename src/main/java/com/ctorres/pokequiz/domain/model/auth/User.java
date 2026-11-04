package com.ctorres.pokequiz.domain.model.auth;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public record User(
        UUID id,
        String username,
        String passwordHash,
        boolean active,
        Set<Role> roles
) {
    public static User create(
            UUID id,
            String username,
            String passwordHash
    ) {
        return new User(
                Objects.requireNonNull(id),
                Objects.requireNonNull(username),
                Objects.requireNonNull(passwordHash),
                true,
                new HashSet<>()
        );
    }

    public static User restore(
            UUID id,
            String username,
            String passwordHash,
            boolean active,
            Set<Role> roles
    ) {
        return new User(
                Objects.requireNonNull(id),
                Objects.requireNonNull(username),
                Objects.requireNonNull(passwordHash),
                active,
                Objects.requireNonNull(roles)
        );
    }
}
