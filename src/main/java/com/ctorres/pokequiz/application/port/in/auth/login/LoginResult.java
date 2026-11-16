package com.ctorres.pokequiz.application.port.in.auth.login;

import java.util.Objects;

public record LoginResult(
        String accessToken,
        String refreshToken
) {

    public static LoginResult of(String accessToken, String refreshToken) {
        return new LoginResult(
                Objects.requireNonNull(accessToken),
                Objects.requireNonNull(refreshToken)
        );
    }
}