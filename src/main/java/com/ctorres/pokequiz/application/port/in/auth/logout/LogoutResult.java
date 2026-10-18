package com.ctorres.pokequiz.application.port.in.auth.refresh;

import com.ctorres.pokequiz.infrastructure.web.response.auth.TokenResponse;

public record RefreshResult(
        TokenResponse tokenResponse
) {}