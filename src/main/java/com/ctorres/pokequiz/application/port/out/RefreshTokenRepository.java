package com.ctorres.pokequiz.application.port.out;

import com.ctorres.pokequiz.domain.model.auth.RefreshToken;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository {
    Optional<RefreshToken> findByTokenHashAndRevokedAtIsNullAndExpiresAtAfter(String tokenHash, OffsetDateTime now);
    int revokeAllByUserId(UUID userId);
    int revokeByHash(String hash);
    RefreshToken save(RefreshToken refreshToken);
}
