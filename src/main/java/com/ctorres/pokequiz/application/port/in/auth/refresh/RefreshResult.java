package com.ctorres.pokequiz.application.port.in.auth.refresh;

import java.util.Objects;

public record RefreshResult(
        String accessToken,
        String refreshToken
) {
    public static RefreshResult of(String accessToken, String refreshToken) {
        return new RefreshResult(
                Objects.requireNonNull(accessToken),
                Objects.requireNonNull(refreshToken)
        );
    }
}