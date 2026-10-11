package com.ctorres.pokequiz.application.usecase.auth.exception;

public class WeakPasswordException extends RuntimeException {

    public WeakPasswordException() {
        super("Password is weak");
    }
}
