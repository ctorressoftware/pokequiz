package com.ctorres.pokequiz.application.port.in.auth.register;

import java.util.Objects;

public record RegisterResult(
        String accessToken,
        String refreshToken
) {
    public static RegisterResult of(String accessToken, String refreshToken) {
        return new RegisterResult(
                Objects.requireNonNull(accessToken),
                Objects.requireNonNull(refreshToken)
        );
    }
}