package com.ctorres.pokequiz.infrastructure.persistence.repository;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.ctorres.pokequiz.infrastructure.persistence.entity.RefreshTokenEntity;

public interface SpringDataRefreshTokenRepository extends JpaRepository<RefreshTokenEntity, UUID> {

    Optional<RefreshTokenEntity> findByTokenHashAndRevokedAtIsNull(String tokenHash);
    Optional<RefreshTokenEntity> findByTokenHashAndRevokedAtIsNullAndExpiresAtAfter(String tokenHash, OffsetDateTime now);

    @Modifying
    @Query("update RefreshTokenEntity rt set rt.revokedAt = CURRENT_TIMESTAMP " +
           "where rt.userId = :userId and rt.revokedAt is null")
    int revokeAllByUserId(@Param("userId") UUID userId);

    @Modifying
    @Query("update RefreshTokenEntity rt set rt.revokedAt = CURRENT_TIMESTAMP " +
           "where rt.tokenHash = :hash and rt.revokedAt is null")
    int revokeByHash(@Param("hash") String hash);

    @Modifying
    @Query("delete from RefreshTokenEntity rt where rt.expiresAt < :now")
    int deleteAllExpired(@Param("now") OffsetDateTime now);
}