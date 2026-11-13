
package com.ctorres.pokequiz.infrastructure.persistence.adapter;

import com.ctorres.pokequiz.application.port.out.RefreshTokenRepository;
import com.ctorres.pokequiz.domain.model.auth.RefreshToken;
import com.ctorres.pokequiz.infrastructure.mapper.RefreshTokenMapper;
import com.ctorres.pokequiz.infrastructure.persistence.entity.RefreshTokenEntity;
import com.ctorres.pokequiz.infrastructure.persistence.repository.SpringDataRefreshTokenRepository;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
public class JpaRefreshTokenRepositoryAdapter implements RefreshTokenRepository {
    private final SpringDataRefreshTokenRepository repository;
    private final RefreshTokenMapper refreshTokenMapper;

    public JpaRefreshTokenRepositoryAdapter(SpringDataRefreshTokenRepository repository) {
        this.repository = repository;
        this.refreshTokenMapper = new RefreshTokenMapper();
    }

    @Override
    public Optional<RefreshToken> findByTokenHashAndRevokedAtIsNullAndExpiresAtAfter(
            String tokenHash,
            OffsetDateTime now
    ) {
        Optional<RefreshTokenEntity> entities =
                repository.findByTokenHashAndRevokedAtIsNullAndExpiresAtAfter(tokenHash, OffsetDateTime.now());
        return entities.map(refreshTokenMapper::map);
    }

    @Override
    public int revokeAllByUserId(UUID userId) {
        return repository.revokeAllByUserId(userId);
    }

    @Override
    public int revokeByHash(String hash) {
        return repository.revokeByHash(hash);
    }

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        RefreshTokenEntity entity = refreshTokenMapper.map(refreshToken);
        RefreshTokenEntity saved = repository.save(entity);
        return refreshTokenMapper.map(saved);
    }
}
