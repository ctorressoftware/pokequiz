package com.ctorres.pokequiz.domain.model.auth;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

public final class RefreshToken {
    private final UUID id;
    private final UUID userId;
    private final String tokenHash;
    private final OffsetDateTime issuedAt;
    private final OffsetDateTime expiresAt;
    private OffsetDateTime revokedAt;
    private String replacedByHash;
    private final String ip;
    private final String userAgent;

    public RefreshToken(
            UUID id,
            UUID userId,
            String tokenHash,
            OffsetDateTime issuedAt,
            OffsetDateTime expiresAt,
            OffsetDateTime revokedAt,
            String replacedByHash,
            String ip,
            String userAgent
    ) {
        this.id = id;
        this.userId = userId;
        this.tokenHash = tokenHash;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
        this.revokedAt = revokedAt;
        this.replacedByHash = replacedByHash;
        this.ip = ip;
        this.userAgent = userAgent;
    }

    public static RefreshToken create(
            UUID userId,
            String tokenHash,
            OffsetDateTime issuedAt,
            OffsetDateTime expiresAt,
            String ip,
            String userAgent
    ) {
        return new RefreshToken(
                UUID.randomUUID(),
                Objects.requireNonNull(userId),
                Objects.requireNonNull(tokenHash),
                Objects.requireNonNull(issuedAt),
                Objects.requireNonNull(expiresAt),
                null,
                null,
                ip,
                userAgent
        );
    }

    public static RefreshToken retrieve(
            UUID id,
            UUID userId,
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
                userId,
                tokenHash,
                issuedAt,
                expiresAt,
                revokedAt,
                replacedByHash,
                ip,
                userAgent
        );
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setRevokedAt(OffsetDateTime revokedAt) {
        this.revokedAt = revokedAt;
    }

    public void setReplacedByHash(String replacedByHash) {
        this.replacedByHash = replacedByHash;
    }

    public String getTokenHash() {
        return tokenHash;
    }

    public OffsetDateTime getIssuedAt() {
        return issuedAt;
    }

    public OffsetDateTime getExpiresAt() {
        return expiresAt;
    }

    public OffsetDateTime getRevokedAt() {
        return revokedAt;
    }

    public String getReplacedByHash() {
        return replacedByHash;
    }

    public String getIp() {
        return ip;
    }

    public String getUserAgent() {
        return userAgent;
    }
}
