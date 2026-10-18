package com.ctorres.pokequiz.application.port.in.auth.register;

import jakarta.validation.Valid;

public record RegisterCommand(
        @Valid String username,
        @Valid String password,
        @Valid String ip,
        @Valid String userAgent
) {}
