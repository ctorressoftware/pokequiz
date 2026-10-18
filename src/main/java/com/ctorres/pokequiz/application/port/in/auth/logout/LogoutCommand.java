package com.ctorres.pokequiz.application.port.in.auth.logout;

import jakarta.validation.Valid;

public record LogoutCommand(@Valid String refreshTokenValue) {}
