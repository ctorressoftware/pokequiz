package com.ctorres.pokequiz.application.port.in.auth.register;

public interface RegisterUseCase {
    RegisterResult register(RegisterCommand command);
}