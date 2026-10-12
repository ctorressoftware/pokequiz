package com.ctorres.pokequiz.application.port.in.auth.login;

public interface LoginUseCase {
    LoginResult login(LoginCommand command);
}