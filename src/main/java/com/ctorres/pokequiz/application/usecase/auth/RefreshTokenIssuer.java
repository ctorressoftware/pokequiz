package com.ctorres.pokequiz.application.usecase.auth;

import com.ctorres.pokequiz.application.port.out.RefreshTokenRepository;
import com.ctorres.pokequiz.domain.model.auth.User;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HexFormat;
import java.util.UUID;

@Component
public class RefreshTokenIssuer {

    private final Clock clock;
    private final RefreshTokenRepository repository;

    public RefreshTokenIssuer(Clock clock, RefreshTokenRepository repository) {
        this.clock = clock;
        this.repository = repository;
    }

    public RefreshToken issueRefreshToken(User user, String ip, String userAgent) {

        String value = newRefreshTokenValue();
        OffsetDateTime now = OffsetDateTime.now(clock).truncatedTo(ChronoUnit.SECONDS);

        long REFRESH_DAYS = 15;
        var refreshToken = com.ctorres.pokequiz.domain.model.auth.RefreshToken.create(
                user.id(),
                sha256Hex(value),
                now,
                now.plusDays(REFRESH_DAYS),
                ip,
                userAgent
        );

        repository.save(refreshToken);
        return new RefreshToken(refreshToken.getTokenHash(), value);
    }

    private String sha256Hex(String s) {
        try {
            var md = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(md.digest(s.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private String newRefreshTokenValue() {
        return UUID.randomUUID() + "." + UUID.randomUUID();
    }
}
