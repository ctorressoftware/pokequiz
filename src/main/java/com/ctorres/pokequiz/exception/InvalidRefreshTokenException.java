package com.ctorres.pokequiz.exception;

public class InvalidRefreshTokenException extends RuntimeException {

    public InvalidRefreshTokenException() {
        super("Invalid refresh token. You must login again.");
    }
}
