package com.ctorres.pokequiz.application.port.in.auth.login;

import jakarta.validation.Valid;

public record LoginCommand (
        @Valid String username,
        @Valid String rawPassword,
        @Valid String ip,
        @Valid String userAgent
) {}
