package com.ctorres.pokequiz.domain.model.auth;

import com.ctorres.pokequiz.entity.Role;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public record User(
        Long id,
        String username,
        String passwordHash,
        boolean active,
        Set<Role> roles
) {
    public static User create(
            Long id,
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

    public static User retrieve(
            Long id,
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
                roles
        );
    }
}
