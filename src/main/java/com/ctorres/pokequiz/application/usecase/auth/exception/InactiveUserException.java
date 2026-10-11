package com.ctorres.pokequiz.application.usecase.auth.exception;

public class InactiveUserException extends RuntimeException {

    public InactiveUserException() {
        super("The user is inactive");
    }
}
