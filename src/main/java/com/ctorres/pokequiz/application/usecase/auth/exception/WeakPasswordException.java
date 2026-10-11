package com.ctorres.pokequiz.exception;

public class WeakPasswordException extends RuntimeException {

    public WeakPasswordException() {
        super("Password is weak");
    }
}
