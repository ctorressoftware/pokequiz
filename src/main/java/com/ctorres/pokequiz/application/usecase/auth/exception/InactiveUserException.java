package com.ctorres.pokequiz.exception;

public class InactiveUserException extends RuntimeException {

    public InactiveUserException() {
        super("The user is inactive");
    }
}
