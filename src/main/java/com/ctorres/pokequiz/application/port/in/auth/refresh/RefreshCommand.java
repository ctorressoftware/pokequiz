package com.ctorres.pokequiz.application.port.in.auth.refresh;

import jakarta.validation.Valid;

public record RefreshCommand(
        @Valid String refreshTokenValue,
        @Valid String ip,
        @Valid String userAgent
) {}
