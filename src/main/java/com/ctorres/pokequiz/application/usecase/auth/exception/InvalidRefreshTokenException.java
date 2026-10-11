package com.ctorres.pokequiz.application.usecase.auth.exception;

public class InvalidRefreshTokenException extends RuntimeException {

    public InvalidRefreshTokenException() {
        super("Invalid refresh token. Must login again");
    }
}
