package com.ctorres.pokequiz.application.port.in.auth.register;

import com.ctorres.pokequiz.infrastructure.web.response.auth.TokenResponse;

public record RegisterResult(
        TokenResponse tokenResponse
) {}