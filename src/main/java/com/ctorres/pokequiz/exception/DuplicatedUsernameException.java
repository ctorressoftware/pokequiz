package com.ctorres.pokequiz.exception;

public class DuplicatedUsernameException extends RuntimeException {
    public DuplicatedUsernameException() {
        super("The username alredy exists");
    }
}
