package com.ctorres.pokequiz.dto.auth;

import java.util.Objects;

public class TokenResponse {

    private final String accessToken;
    private final String refreshToken;

    private TokenResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public static Builder builder() {
        return new Builder();
    }

    public final static class Builder {
        private String accessToken;
        private String refreshToken;

        public Builder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public Builder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public TokenResponse build() {
            return new TokenResponse(
                    Objects.requireNonNull(accessToken, "accessToken is required"),
                    Objects.requireNonNull(refreshToken, "refreshToken is required")
            );
        }
    }
}
