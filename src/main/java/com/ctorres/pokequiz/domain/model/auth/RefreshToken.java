package com.ctorres.pokequiz.domain.model.auth;

import java.time.OffsetDateTime;
import java.util.Objects;

public record RefreshToken(
        Long id,
        User user,
        String tokenHash,
        OffsetDateTime issuedAt,
        OffsetDateTime expiresAt,
        OffsetDateTime revokedAt,
        String replacedByHash,
        String ip,
        String userAgent
) {
    public static RefreshToken create(
            Long id,
            User user,
            String tokenHash,
            OffsetDateTime issuedAt,
            OffsetDateTime expiresAt,
            String ip,
            String userAgent
    ) {
        return new RefreshToken(
                Objects.requireNonNull(id),
                Objects.requireNonNull(user),
                Objects.requireNonNull(tokenHash),
                Objects.requireNonNull(issuedAt),
                Objects.requireNonNull(expiresAt),
                null,
                null,
                Objects.requireNonNull(ip),
                Objects.requireNonNull(userAgent)
        );
    }

    public static RefreshToken retrieve(
            Long id,
            User user,
            String tokenHash,
            OffsetDateTime issuedAt,
            OffsetDateTime expiresAt,
            OffsetDateTime revokedAt,
            String replacedByHash,
            String ip,
            String userAgent
    ) {
        return new RefreshToken(
                id,
                user,
                tokenHash,
                issuedAt,
                expiresAt,
                revokedAt,
                replacedByHash,
                ip,
                userAgent
        );
    }
}
