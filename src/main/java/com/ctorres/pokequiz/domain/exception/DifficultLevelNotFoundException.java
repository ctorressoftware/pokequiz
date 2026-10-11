package com.ctorres.pokequiz.exception;

public class DifficultLevelNotFoundException extends RuntimeException {

    public DifficultLevelNotFoundException(Long id) {
        super("The difficult level with ID = " + id + " doesn't exists");
    }
}
