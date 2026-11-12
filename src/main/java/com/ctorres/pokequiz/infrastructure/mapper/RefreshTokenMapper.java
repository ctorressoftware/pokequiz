
package com.ctorres.pokequiz.infrastructure.mapper;

import com.ctorres.pokequiz.domain.model.auth.RefreshToken;
import com.ctorres.pokequiz.infrastructure.persistence.entity.RefreshTokenEntity;

public class RefreshTokenMapper {

    public RefreshTokenEntity map(RefreshToken domain) {
        if (domain == null) {
            return null;
        }

        RefreshTokenEntity entity = new RefreshTokenEntity();

        entity.setId(domain.getId());
        entity.setUserId(domain.getUserId());
        entity.setTokenHash(domain.getTokenHash());
        entity.setIssuedAt(domain.getIssuedAt());
        entity.setExpiresAt(domain.getExpiresAt());
        entity.setRevokedAt(domain.getRevokedAt());
        entity.setReplacedByHash(domain.getReplacedByHash());
        entity.setIp(domain.getIp());
        entity.setUserAgent(domain.getUserAgent());

        return entity;
    }

    public RefreshToken map(RefreshTokenEntity entity) {
        if (entity == null) {
            return null;
        }

        return new RefreshToken(
                entity.getId(),
                entity.getUserId(),
                entity.getTokenHash(),
                entity.getIssuedAt(),
                entity.getExpiresAt(),
                entity.getRevokedAt(),
                entity.getReplacedByHash(),
                entity.getIp(),
                entity.getUserAgent()
        );
    }
}
