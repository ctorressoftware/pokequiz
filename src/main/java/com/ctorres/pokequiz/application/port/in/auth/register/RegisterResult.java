package com.ctorres.pokequiz.application.port.in.auth.login;

import com.ctorres.pokequiz.infrastructure.web.response.auth.TokenResponse;

public record LoginResult(
        TokenResponse tokenResponse
) {}