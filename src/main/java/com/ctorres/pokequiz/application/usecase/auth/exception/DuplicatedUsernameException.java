package com.ctorres.pokequiz.application.usecase.auth.exception;

public class DuplicatedUsernameException extends RuntimeException {
    public DuplicatedUsernameException() {
        super("The username alredy exists");
    }

    public DuplicatedUsernameException(String username) {
        super("The username " + username + " alredy exists");
    }
}
