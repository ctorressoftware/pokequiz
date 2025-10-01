package com.ctorres.pokequiz.repository;

import java.time.OffsetDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.ctorres.pokequiz.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByTokenHashAndRevokedAtIsNull(String tokenHash);
    Optional<RefreshToken> findByTokenHashAndRevokedAtIsNullAndExpiresAtAfter(String tokenHash, OffsetDateTime now);

    @Modifying
    @Query("update RefreshToken rt set rt.revokedAt = CURRENT_TIMESTAMP " +
           "where rt.user.id = :userId and rt.revokedAt is null")
    int revokeAllByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("update RefreshToken rt set rt.revokedAt = CURRENT_TIMESTAMP " +
           "where rt.tokenHash = :hash and rt.revokedAt is null")
    int revokeByHash(@Param("hash") String hash);

    @Modifying
    @Query("delete from RefreshToken rt where rt.expiresAt < :now")
    int deleteAllExpired(@Param("now") OffsetDateTime now);
}