package com.ctorres.pokequiz.application.port.in.auth.logout;

public interface LogoutUseCase {
    LogoutResult logout(LogoutCommand command);
}